package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.generation.OntologyReductionService
import dev.leonzimmermann.bachelorprojekt.services.ontology.OntologyReductionOptions
import org.apache.jena.ontology.OntClass
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntProperty
import org.springframework.stereotype.Service

@Service
class OntologyReductionServiceImpl : OntologyReductionService {

  override fun reduceOntology(
    ontModel: OntModel,
    ontologyReductionOptions: OntologyReductionOptions
  ): OntModel {
    // IMPORTANT! Fetching of OntClasses is expensive!
    val ontClasses = ontModel.listClasses().toList()
    if (ontologyReductionOptions.numberOfTables > ontClasses.size) {
      return ontModel
    }
    val sortedClasses = ontClasses.sortedByDescending { it.listDeclaredProperties().toList().size }
    replaceReferencesToSmallerEntitiesWithPrimitiveDatatype(sortedClasses, ontologyReductionOptions, ontModel)
    removeSmallerEntities(sortedClasses, ontologyReductionOptions)
    return ontModel
  }

  private fun replaceReferencesToSmallerEntitiesWithPrimitiveDatatype(
    sortedClasses: List<OntClass>,
    ontologyReductionOptions: OntologyReductionOptions,
    ontModel: OntModel
  ) {
    val largestEntities = sortedClasses.subList(0, ontologyReductionOptions.numberOfTables)
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
    ontologyReductionOptions: OntologyReductionOptions
  ) {
    sortedClasses.subList(ontologyReductionOptions.numberOfTables, sortedClasses.size).forEach {
      it.remove()
    }
  }

}