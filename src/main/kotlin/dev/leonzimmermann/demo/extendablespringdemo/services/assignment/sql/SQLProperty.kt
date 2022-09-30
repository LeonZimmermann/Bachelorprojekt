package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

open class SQLProperty(protected val propertyName: String): SQLElement {
  override fun toSQLString(): String = propertyName
}