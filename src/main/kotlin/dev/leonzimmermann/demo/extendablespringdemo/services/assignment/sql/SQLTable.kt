package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

class SQLTable(private val tableName: String): SQLElement {
  override fun toSQLString(): String = tableName
}
