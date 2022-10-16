package dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme

data class ForeignKeyScheme(
  val propertyName: String,
  val referenceTable: String,
  val referenceField: String
)