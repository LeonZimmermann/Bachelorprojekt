package dev.leonzimmermann.bachelorprojekt.services.database.scheme

data class ForeignKeyScheme(
  val propertyName: String,
  val referenceTable: String,
  val referenceField: String
)