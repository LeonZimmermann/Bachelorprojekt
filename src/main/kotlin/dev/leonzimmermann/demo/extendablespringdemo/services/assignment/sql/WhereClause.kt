package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

class WhereClause(private val booleanExpression: BooleanExpression): SQLElement {
  override fun toSQLString(): String = "WHERE ${booleanExpression.toSQLString()}"
}
