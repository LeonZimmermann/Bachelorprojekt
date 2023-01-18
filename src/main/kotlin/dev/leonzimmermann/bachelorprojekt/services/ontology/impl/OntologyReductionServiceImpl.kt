package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.generation.DatabaseOptions
import dev.leonzimmermann.bachelorprojekt.generation.OntologyReductionService
import org.apache.jena.ontology.OntClass
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntProperty
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

  private fun replaceObjectPropertyByDatatypeProperty(ontModel: OntModel, it: OntProperty) {
    ontModel.createDatatypeProperty(it.localName).apply {
      setLabel(it.getLabel("EN"), "EN")
      setRange(ontModel.getResource("xsd:string"))
      setDomain(it.domain)
    }
    it.remove()
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
