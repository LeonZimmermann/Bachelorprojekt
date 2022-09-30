package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

class SQLLiteral(private val propertyName: String): SQLElement {
  override fun toSQLString(): String = propertyName
}