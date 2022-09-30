package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

sealed class BooleanExpression : SQLElement
class AndExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "(${expressionOne.toSQLString()} AND ${expressionTwo.toSQLString()})"
}

class OrExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "(${expressionOne.toSQLString()} OR ${expressionTwo.toSQLString()})"
}

class EqualsExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}=${expressionTwo.toSQLString()}"
}

class NotEqualsExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}!=${expressionTwo.toSQLString()}"
}

class GreaterExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}>${expressionTwo.toSQLString()}"
}

class GreaterEqualsExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}>=${expressionTwo.toSQLString()}"
}

class SmallerExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}<${expressionTwo.toSQLString()}"
}

class SmallerEqualsExpression(
  private val expressionOne: BooleanExpression,
  private val expressionTwo: BooleanExpression
) : BooleanExpression() {
  override fun toSQLString(): String =
    "${expressionOne.toSQLString()}<=${expressionTwo.toSQLString()}"
}

class BooleanExpressionProperty(private val property: SQLProperty) : BooleanExpression() {
  override fun toSQLString(): String = property.toSQLString()
}

class BooleanExpressionLiteral(private val literal: SQLLiteral) : BooleanExpression() {
  override fun toSQLString(): String = literal.toSQLString()
}

object TrueExpression: BooleanExpression() {
  override fun toSQLString(): String = "True"
}

object FalseExpression: BooleanExpression() {
  override fun toSQLString(): String = "False"
}