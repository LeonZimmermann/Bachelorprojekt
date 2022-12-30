package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.assignment.OntologyService
import dev.leonzimmermann.bachelorprojekt.isNumber
import dev.leonzimmermann.bachelorprojekt.isString
import org.apache.jena.ontology.*
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.springframework.stereotype.Service
import java.util.*

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
      .asSequence()
      .mapNotNull { it.domain }
      .mapNotNull { it.uri }
      .map { ontModel.getOntClass(it) }
      .distinct()
      .toList()

  private fun createOutputModel(tables: List<OntClass>): OntModel {
    val result = ModelFactory.createOntologyModel()
    tables.forEach { table ->
      val ontClass = result.createClass(table.localName)
      ontClass.setLabel(getLabelFor(table).replaceFirstChar { it.titlecase(Locale.getDefault()) }, "EN")
      PropertyMapper(result).mapPropertiesToResult(table.listDeclaredProperties().toList())
      table.listInstances().toList().forEach { instance ->
        mapInstanceToResult(ontClass, instance)
      }
    }
    return result
  }

  private fun mapInstanceToResult(ontClass: OntClass, instance: OntResource) {
    ontClass.createIndividual(instance.localName)
      .setLabel(getLabelFor(instance), "EN")
  }

  private fun getLabelFor(resource: OntResource) =
    (resource.getLabel("EN") ?: resource.localName)
      .trim()
      .replace(" ", "_")
}