package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.generation.OntologyReductionService
import org.apache.jena.ontology.OntModel
import org.springframework.stereotype.Service

@Service
class OntologyReductionServiceImpl : OntologyReductionService {

  override fun reduceOntology(ontModel: OntModel): OntModel {
    largestEntities(ontModel).forEach { entity ->
      entity.listDeclaredProperties().toList()
        .filter { it.range !in largestEntities(ontModel) }
        .forEach {
          it.convertToDatatypeProperty()
          it.setRange(ontModel.getResource("xsd:string"))
        }
    }
    classesSortedByPropertyNumber(ontModel)
      .subList(NUMBER_OF_TABLES, classesSortedByPropertyNumber(ontModel).size)
      .forEach { it.remove() }
    return ontModel
  }

  private fun classesSortedByPropertyNumber(ontModel: OntModel) =
    ontModel.listClasses().toList()
      .sortedByDescending { it.listDeclaredProperties().toList().size }

  private fun largestEntities(ontModel: OntModel) =
    classesSortedByPropertyNumber(ontModel).subList(0, NUMBER_OF_TABLES)

  companion object {
    private const val NUMBER_OF_TABLES = 5
  }
}