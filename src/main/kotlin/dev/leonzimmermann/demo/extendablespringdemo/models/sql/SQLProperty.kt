package dev.leonzimmermann.demo.extendablespringdemo.models.sql

open class SQLProperty(protected val propertyName: String): SQLElement {
  override fun toSQLString(): String = propertyName
}