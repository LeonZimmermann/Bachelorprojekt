package dev.leonzimmermann.demo.extendablespringdemo.models.sql

class SelectStatement(
  private val selectProperties: Array<SelectProperty>,
  private val tableStatement: TableStatement,
  private val whereClause: WhereClause? = null
) : SQLElement {
  override fun toSQLString(): String =
    "SELECT ${selectProperties.joinToString(",") { it.toSQLString() }} ${tableStatement.toSQLString()} ${whereClause?.toSQLString() ?: ""}"
}