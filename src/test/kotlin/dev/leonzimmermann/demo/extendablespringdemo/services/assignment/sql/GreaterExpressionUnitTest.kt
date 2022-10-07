package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import org.junit.Test

import org.junit.Assert.*

class GreaterExpressionUnitTest: AbstractSQLUnitTest() {

  @Test
  fun testWithAPropertyAndALiteral() {
    val statement = GreaterExpression(
      BooleanExpressionProperty(SQLProperty("postalcode")),
      BooleanExpressionLiteral(SQLNumberLiteral(2000))
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("Postalcode is greater than 2000.", realisedSentence)
    assertEquals("postalcode>2000", statement.toSQLString())
  }

  @Test
  fun testWithAPropertyALiteralAndARelationWord() {
    val statement = GreaterExpression(
      BooleanExpressionProperty(SQLProperty("mountain")),
      BooleanExpressionLiteral(SQLNumberLiteral(300, "m")),
      "be higher"
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("Mountain is higher than 300m.", realisedSentence)
    assertEquals("mountain>300", statement.toSQLString())
  }
}