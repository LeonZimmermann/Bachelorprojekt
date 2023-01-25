package dev.leonzimmermann.bachelorprojekt

import org.apache.jena.ontology.OntProperty

fun isNumber(property: OntProperty): Boolean =
  arrayOf(
    "real", "rational", "decimal", "integer", "nonNegativeInteger", "nonPositiveInteger",
    "positiveInteger", "negativeInteger", "long", "int", "short", "byte",
    "unsignedLong", "unsignedInt", "unsignedShort", "unsignedByte"
  ).contains(property.range.localName)

fun isInt(property: OntProperty): Boolean =
  arrayOf(
    "integer", "nonNegativeInteger", "nonPositiveInteger", "positiveInteger",
    "negativeInteger", "long", "int", "short", "byte", "unsignedLong", "unsignedInt",
    "unsignedShort", "unsignedByte"
  ).contains(property.range.localName)

fun isBoolean(property: OntProperty): Boolean = "boolean".equals(property.range.localName)

fun isFloat(property: OntProperty): Boolean =
  arrayOf("real", "rational", "decimal").contains(property.range.localName)

fun isString(property: OntProperty) =
  property.range.localName.lowercase().contains("string")
