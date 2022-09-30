package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.features.Feature
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

/**
 * all valid root-elements have to inherit from SQLExpression
 */
sealed class SQLExpression : SQLElement

class SelectStatement(
  private val selectProperties: SQLEnumeration<SQLProperty>,
  private val fromStatement: FromStatement,
  private val whereClause: WhereClause? = null
) : SQLExpression() {
  override fun toSQLString(): String =
    "SELECT ${selectProperties.toSQLString()} ${fromStatement.toSQLString()} ${whereClause?.toSQLString() ?: ""}"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val clause = nlgFactory.createClause()
    clause.setVerb("get")
    clause.setObject(selectProperties.toStemText(nlgFactory))
    if (whereClause != null) {
      clause.setComplement(whereClause.toStemText(nlgFactory))
    }
    clause.setFeature(Feature.EXCLAMATORY, true)
    clause.setFeature(Feature.PERSON, "You")
    return clause
  }
}