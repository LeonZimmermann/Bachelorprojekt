package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

class FromStatement(private val tableName: SQLTable, private val tableAlias: SQLLiteral? = null):
  SQLElement {
  override fun toSQLString(): String {
    var result = "FROM ${tableName.toSQLString()}"
    tableAlias?.let {
      result += " AS ${it.toSQLString()}"
    }
    return result
  }
}
