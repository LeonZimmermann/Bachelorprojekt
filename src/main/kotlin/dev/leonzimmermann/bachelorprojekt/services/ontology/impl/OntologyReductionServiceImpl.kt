package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.generation.DatabaseOptions
import dev.leonzimmermann.bachelorprojekt.generation.OntologyReductionService
import org.apache.jena.ontology.OntClass
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntProperty
import org.apache.jena.rdf.model.Resource
import org.springframework.stereotype.Service

@Service
class OntologyReductionServiceImpl : OntologyReductionService {

  override fun reduceOntology(
    ontModel: OntModel,
    databaseOptions: DatabaseOptions
  ): OntModel {
    // IMPORTANT! Fetching of OntClasses is expensive!
    val ontClasses = ontModel.listClasses().toList()
    if (databaseOptions.numberOfTables > ontClasses.size) {
      return ontModel
    }
    val sortedClasses = ontClasses.sortedByDescending { it.listDeclaredProperties().toList().size }
    replaceReferencesToSmallerEntitiesWithPrimitiveDatatype(sortedClasses, databaseOptions, ontModel)
    removeSmallerEntities(sortedClasses, databaseOptions)
    return ontModel
  }

  private fun replaceReferencesToSmallerEntitiesWithPrimitiveDatatype(
    sortedClasses: List<OntClass>,
    databaseOptions: DatabaseOptions,
    ontModel: OntModel
  ) {
    val largestEntities = sortedClasses.subList(0, databaseOptions.numberOfTables)
    largestEntities.forEach { entity ->
        entity.listDeclaredProperties().toList()
          .filter {
            it.range !in largestEntities
          }
          .forEach { replaceObjectPropertyByDatatypeProperty(ontModel, it) }
      }
  }

  private fun replaceObjectPropertyByDatatypeProperty(ontModel: OntModel, property: OntProperty) {
    ontModel.createDatatypeProperty(property.localName).apply {
      setLabel(property.getLabel("EN"), "EN")
      setRange(searchPrimitiveType(ontModel, property))
      setDomain(property.domain)
    }
    property.remove()
  }

  private fun searchPrimitiveType(ontModel: OntModel, property: OntProperty, depth: Int = 0): Resource {
    if (depth >= 20) {
      return ontModel.getResource("xsd:string")
    }
      return if (property.isObjectProperty) {
          ontModel.getOntClass(property.range.uri).listDeclaredProperties().toList().map {
              searchPrimitiveType(ontModel, property, depth + 1)
          }.find { it.uri != "xsd:string" } ?: ontModel.getResource("xsd:string")
      } else {
          property.range
      }
  }

  private fun removeSmallerEntities(
    sortedClasses: List<OntClass>,
    databaseOptions: DatabaseOptions
  ) {
    sortedClasses.subList(databaseOptions.numberOfTables, sortedClasses.size).forEach {
      it.remove()
    }
  }

}
