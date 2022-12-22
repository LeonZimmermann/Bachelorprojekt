package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.assignment.OntologyService
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
      table.listDeclaredProperties().toList().forEach { property ->
        mapPropertyToResult(property, result)
      }
      table.listInstances().toList().forEach { instance ->
        mapInstanceToResult(ontClass, instance)
      }
    }
    return result
  }

  private fun mapPropertyToResult(property: OntProperty, model: OntModel) {
    if (property.domain == null ||
      property.range == null ||
      property.range.localName == null
    ) {
      return
    }
    if (property.isObjectProperty) {
      mapObjectPropertyToResult(model, property)
    } else if (property.isDatatypeProperty) {
      mapDatatypePropertyToResult(property, model)
    }
  }

  private fun mapObjectPropertyToResult(
    model: OntModel,
    property: OntProperty
  ) {
    val referenceToNewObjectProperty = model.createObjectProperty(property.localName)
    referenceToNewObjectProperty.setLabel(getLabelFor(property), "EN")
    referenceToNewObjectProperty.setDomain(
      model.getOntClass(property.domain.localName) ?: property.domain
    )
    referenceToNewObjectProperty.setRange(property.range)
  }

  private fun mapDatatypePropertyToResult(property: OntProperty, model: OntModel) {
    if (!property.range.localName.lowercase().contains("integer") ||
      !property.range.localName.lowercase().contains("double") ||
      !property.range.localName.lowercase().contains("string")
    ) {
      return
    }
    val referenceToNewDatatypeProperty = model.createDatatypeProperty(property.localName)
    referenceToNewDatatypeProperty.setLabel(getLabelFor(property), "EN")
    referenceToNewDatatypeProperty.setDomain(
      model.getOntClass(property.domain.localName) ?: property.domain
    )
    referenceToNewDatatypeProperty.setRange(property.range)
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