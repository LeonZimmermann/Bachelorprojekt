package dev.leonzimmermann.demo.extendablespringdemo.models.sql

sealed class SelectProperty: SQLElement

class BasicProperty(private val propertyName: String): SelectProperty() {
  override fun toSQLString(): String = propertyName
}

class CountProperty(private val propertyName: String): SelectProperty() {
  override fun toSQLString(): String = "COUNT($propertyName)"
}