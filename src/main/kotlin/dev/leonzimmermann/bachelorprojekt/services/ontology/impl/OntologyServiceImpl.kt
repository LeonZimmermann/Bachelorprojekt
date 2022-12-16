package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.assignment.OntologyService
import org.apache.jena.ontology.*
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.springframework.stereotype.Service

@Service
class OntologyServiceImpl : OntologyService {

  /**
   * The result has to be a normalized Ontology, i.e. an ontology that only has entities and relationships
   * that correspond to ER-Diagrams.
   */
  override fun createEROntology(uri: String): OntModel {
    return loadModel(uri)
      .let { extractTables(it) }
      .let { createOutputModel(it) }
  }

  private fun loadModel(uri: String) = ModelFactory.createOntologyModel(
    OntModelSpec(OntModelSpec.OWL_MEM),
    RDFDataMgr.loadModel(uri)
  )

  private fun extractTables(ontModel: OntModel) =
    ontModel.listAllOntProperties().toList()
      .mapNotNull { it.domain }
      .map { ontModel.getOntClass(it.uri) }
      .distinct()
      .toList()

  private fun createOutputModel(tables: List<OntClass>): OntModel {
    val result = ModelFactory.createOntologyModel()
    tables.forEach { table ->
      val ontClass = result.createClass(table.localName)
      ontClass.setLabel(table.localName, "EN")
      table.listDeclaredProperties().toList().forEach { property ->
        mapPropertyToResult(property, result)
      }
      table.listInstances().toList().forEach { instance ->
        mapInstanceToResult(ontClass, instance)
      }
    }
    return result
  }

  private fun mapPropertyToResult(property: OntProperty, result: OntModel) {
    if (property.isObjectProperty) {
      result.createObjectProperty(property.localName)
        .setLabel(property.localName, "EN")
    } else if (property.isDatatypeProperty) {
      result.createDatatypeProperty(property.localName)
        .setLabel(property.localName, "EN")
    }
  }

  private fun mapInstanceToResult(ontClass: OntClass, instance: OntResource) {
    ontClass.createIndividual(instance.localName)
      .setLabel(instance.localName, "EN")
  }
}