package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import org.junit.Assert.*
import org.junit.Test

class NotEqualsExpressionUnitTest : AbstractSQLUnitTest() {
  @Test
  fun testToStemTextWithAPropertiesAndALiteral() {
    val statement = NotEqualsExpression(
      BooleanExpressionProperty(SQLProperty("city")),
      BooleanExpressionLiteral(SQLStringLiteral("'Essen'")),
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("City does not equal 'Essen'.", realisedSentence)
  }

  @Test
  fun testToStemTextWithAPropertiesALiteralAndARelationWord() {
    val statement = NotEqualsExpression(
      BooleanExpressionProperty(SQLProperty("city")),
      BooleanExpressionLiteral(SQLStringLiteral("'Essen'")),
      "is"
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("City is not 'Essen'.", realisedSentence)
  }

  @Test
  fun testToStemTextWithAPropertiesAndAnIntegerLiteral() {
    val statement = NotEqualsExpression(
      BooleanExpressionProperty(SQLProperty("postalcode")),
      BooleanExpressionLiteral(SQLNumberLiteral(12345))
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("Postalcode does not equal 12345.", realisedSentence)
  }
}