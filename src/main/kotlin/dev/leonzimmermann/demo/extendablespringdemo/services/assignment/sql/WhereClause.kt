package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

class WhereClause(private val booleanExpression: BooleanExpression) : SQLElement {
  override fun toSQLString(): String = "WHERE ${booleanExpression.toSQLString()}"
  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    booleanExpression.toStemText(nlgFactory)
}
