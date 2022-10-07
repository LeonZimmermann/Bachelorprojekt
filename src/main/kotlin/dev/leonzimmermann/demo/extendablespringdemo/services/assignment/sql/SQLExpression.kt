package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.features.Feature
import simplenlg.features.Person
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

/**
 * all valid root-elements have to inherit from SQLExpression
 */
sealed class SQLExpression : SQLElement

class SelectStatement(
  private val selectProperties: SQLEnumeration<SQLProperty>,
  private val fromStatement: FromStatement,
  private val whereClause: WhereClause? = null,
  private val limitExpression: LimitExpression? = null
) : SQLExpression() {
  override fun toSQLString(): String =
    "SELECT ${selectProperties.toSQLString()} ${fromStatement.toSQLString()} ${whereClause?.toSQLString() ?: ""} ${limitExpression?.toSQLString() ?: ""}"


  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val selectClause = nlgFactory.createClause()
    val queryPhrase = nlgFactory.createVerbPhrase("query")
    queryPhrase.addPostModifier("all")
    selectClause.setVerb(queryPhrase)
    selectClause.setObject(selectProperties.toStemText(nlgFactory))
    val firstSentence = if (whereClause != null) {
      val whereStemText = whereClause.toStemText(nlgFactory)
      val coordinatedPhrase = nlgFactory.createCoordinatedPhrase(selectClause, whereStemText)
      coordinatedPhrase.conjunction = "where"
      coordinatedPhrase.setFeature(Feature.PERSON, Person.SECOND)
      nlgFactory.createSentence(coordinatedPhrase)
    } else {
      selectClause.setFeature(Feature.PERSON, Person.SECOND)
      nlgFactory.createSentence(selectClause)
    }
    val paragraph = nlgFactory.createParagraph()
    paragraph.addComponent(firstSentence)
    if (limitExpression != null) {
      val sentence = nlgFactory.createSentence(limitExpression.toStemText(nlgFactory))
      paragraph.addComponent(sentence)
    }
    return paragraph
  }
}