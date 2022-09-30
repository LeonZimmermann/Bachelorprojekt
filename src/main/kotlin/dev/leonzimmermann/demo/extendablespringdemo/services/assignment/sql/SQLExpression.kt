package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

sealed class SQLExpression: SQLElement

class SelectStatement(
  private val selectProperties: Array<SQLProperty>,
  private val fromStatement: FromStatement,
  private val whereClause: WhereClause? = null
) : SQLExpression() {
  override fun toSQLString(): String =
    "SELECT ${selectProperties.joinToString(",") { it.toSQLString() }} ${fromStatement.toSQLString()} ${whereClause?.toSQLString() ?: ""}"
}