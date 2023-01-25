package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.isBoolean
import dev.leonzimmermann.bachelorprojekt.isNumber
import dev.leonzimmermann.bachelorprojekt.isString
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntProperty
import org.apache.jena.ontology.OntResource

class PropertyMapper(private val model: OntModel) {

  fun mapPropertiesToResult(properties: List<OntProperty>) {
    properties.forEach {
      mapPropertyToResult(it)
    }
  }

  private fun mapPropertyToResult(property: OntProperty) {
    if (property.domain == null ||
      property.range == null ||
      property.range.localName == null
    ) {
      return
    }
    if (property.isObjectProperty) {
      mapObjectPropertyToResult(property)
    } else if (property.isDatatypeProperty) {
      mapDatatypePropertyToResult(property)
    }
  }

  private fun mapObjectPropertyToResult(property: OntProperty) {
    if (objectPropertyIsSelfReferencing(property)) {
      return
    }
    model.createObjectProperty(property.localName).apply {
      setLabel(getLabelFor(property), "EN")
      setDomain(domainOfProperty(property))
      setRange(rangeOfProperty(property))
    }
  }

  private fun mapDatatypePropertyToResult(property: OntProperty) {
    if (!isNumber(property) && !isString(property) && !isBoolean(property)) {
      return
    }
    model.createDatatypeProperty(property.localName).apply {
      setLabel(getLabelFor(property), "EN")
      setDomain(domainOfProperty(property))
      setRange(rangeOfProperty(property))
    }
  }

  private fun objectPropertyIsSelfReferencing(property: OntProperty) =
    domainOfProperty(property) == rangeOfProperty(property)

  private fun domainOfProperty(property: OntProperty): OntResource =
    model.getOntClass(property.domain.localName) ?: property.domain

  private fun rangeOfProperty(property: OntProperty): OntResource =
    model.getOntClass(property.range.localName) ?: property.range

  private fun getLabelFor(resource: OntResource) =
    (resource.getLabel("EN") ?: resource.localName)
      .trim()
      .replace(" ", "_")
}
