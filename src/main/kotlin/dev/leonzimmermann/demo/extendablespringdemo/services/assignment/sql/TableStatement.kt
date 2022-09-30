package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

class TableStatement(private val tableName: String, private val tableAlias: String? = null):
  SQLElement {
  override fun toSQLString(): String {
    var result = "FROM $tableName"
    tableAlias?.let {
      result += " as $it"
    }
    return result
  }
}
