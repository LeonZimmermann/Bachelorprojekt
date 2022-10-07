package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.features.Feature
import simplenlg.framework.LexicalCategory
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

sealed class BooleanExpression : SQLElement
class AndExpression(private vararg val expressions: BooleanExpression) : BooleanExpression() {
  override fun toSQLString(): String =
    expressions.joinToString(" AND ") { "(${it.toSQLString()})" }

  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val coordinatedPhrase = nlgFactory.createCoordinatedPhrase()
    coordinatedPhrase.conjunction = "and"
    expressions.forEach {
      coordinatedPhrase.addCoordinate(it.toStemText(nlgFactory))
    }
    return coordinatedPhrase
  }
}

class OrExpression(private vararg val expressions: BooleanExpression) : BooleanExpression() {
  override fun toSQLString(): String =
    expressions.joinToString(" OR ", "(", ")") { it.toSQLString() }

  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val coordinatedPhrase = nlgFactory.createCoordinatedPhrase()
    coordinatedPhrase.conjunction = "or"
    expressions.forEach {
      coordinatedPhrase.addCoordinate(it.toStemText(nlgFactory))
    }
    return coordinatedPhrase
  }
}

class EqualsExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression,
  private val relationWord: String = "equals to"
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}=${expressionTwo.toSQLString()}"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement = nlgFactory.createClause(
    expressionOne.toStemText(nlgFactory), relationWord, expressionTwo.toStemText(nlgFactory)
  )
}

class NotEqualsExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression,
  private val relationWord: String = "do equal"
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}!=${expressionTwo.toSQLString()}"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val clause = nlgFactory.createClause(
      expressionOne.toStemText(nlgFactory), relationWord, expressionTwo.toStemText(nlgFactory)
    )
    clause.setFeature(Feature.NEGATED, true)
    return clause
  }
}

class GreaterExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression,
  private val relationWord: String = "is greater"
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}>${expressionTwo.toSQLString()}"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val verbPhrase = nlgFactory.createVerbPhrase("$relationWord than")
    verbPhrase.setFeature(Feature.IS_COMPARATIVE, true)
    return nlgFactory.createClause(
      expressionOne.toStemText(nlgFactory), verbPhrase, expressionTwo.toStemText(nlgFactory)
    )
  }
}

class GreaterEqualsExpression(
  private val expressionOne: BooleanExpression, private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}>=${expressionTwo.toSQLString()}"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement = nlgFactory.createClause(
    expressionOne.toStemText(nlgFactory),
    "greater than or equal",
    expressionTwo.toStemText(nlgFactory)
  )
}

class SmallerExpression(
  private val expressionOne: BooleanExpression, private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}<${expressionTwo.toSQLString()}"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement = nlgFactory.createClause(
    expressionOne.toStemText(nlgFactory), "less than", expressionTwo.toStemText(nlgFactory)
  )
}

class SmallerEqualsExpression(
  private val expressionOne: BooleanExpression, private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}<=${expressionTwo.toSQLString()}"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement = nlgFactory.createClause(
    expressionOne.toStemText(nlgFactory), "less than or equal", expressionTwo.toStemText(nlgFactory)
  )
}

class BooleanExpressionProperty(private val property: SQLProperty) : BooleanExpression() {
  override fun toSQLString(): String = property.toSQLString()
  override fun toStemText(nlgFactory: NLGFactory): NLGElement = property.toStemText(nlgFactory)
}

class BooleanExpressionLiteral(private val literal: SQLLiteral) : BooleanExpression() {
  override fun toSQLString(): String = literal.toSQLString()
  override fun toStemText(nlgFactory: NLGFactory): NLGElement = literal.toStemText(nlgFactory)
}

object TrueExpression : BooleanExpression() {
  override fun toSQLString(): String = "True"
  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    nlgFactory.createWord("true", LexicalCategory.ADJECTIVE)
}

object FalseExpression : BooleanExpression() {
  override fun toSQLString(): String = "False"
  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    nlgFactory.createWord("false", LexicalCategory.ADJECTIVE)
}