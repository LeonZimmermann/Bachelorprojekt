package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.generation.OntologyReductionService
import dev.leonzimmermann.bachelorprojekt.services.ontology.OntologyReductionOptions
import org.apache.jena.ontology.OntModel
import org.springframework.stereotype.Service

@Service
class OntologyReductionServiceImpl : OntologyReductionService {

  override fun reduceOntology(
    ontModel: OntModel,
    ontologyReductionOptions: OntologyReductionOptions
  ): OntModel {
    // IMPORTANT! Fetching of OntClasses is expensive!
    val largestEntities = classesSortedByPropertyNumber(ontModel)
      .subList(0, ontologyReductionOptions.numberOfTables)
    largestEntities.forEach { entity ->
      entity.listDeclaredProperties().toList()
        .filter { it.range !in largestEntities }
        .forEach {
          ontModel.createDatatypeProperty(it.localName).apply {
            setLabel(it.getLabel("EN"), "EN")
            setRange(ontModel.getResource("xsd:string"))
            setDomain(it.domain)
          }
          it.remove()
        }
    }
    classesSortedByPropertyNumber(ontModel)
      .subList(
        ontologyReductionOptions.numberOfTables,
        classesSortedByPropertyNumber(ontModel).size
      )
      .forEach { it.remove() }
    return ontModel
  }

  private fun classesSortedByPropertyNumber(ontModel: OntModel) =
    ontModel.listClasses().toList()
      .sortedByDescending { it.listDeclaredProperties().toList().size }
}