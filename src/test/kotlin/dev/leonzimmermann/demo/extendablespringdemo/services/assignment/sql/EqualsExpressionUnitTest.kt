package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import org.junit.Assert.*
import org.junit.Test

class EqualsExpressionUnitTest : AbstractSQLUnitTest() {
  @Test
  fun testToStemTextWithAPropertiesAndALiteral() {
    val statement = EqualsExpression(
      BooleanExpressionProperty(SQLProperty("city")),
      BooleanExpressionLiteral(SQLStringLiteral("'Essen'"))
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("The city equals to 'Essen'.", realisedSentence)
  }

  @Test
  fun testToStemTextWithAPropertiesALiteralAndARelationWord() {
    val statement = EqualsExpression(
      BooleanExpressionProperty(SQLProperty("city")),
      BooleanExpressionLiteral(SQLStringLiteral("'Essen'")),
      "is"
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("The city is 'Essen'.", realisedSentence)
  }

  @Test
  fun testToStemTextWithAPropertiesAndAnIntegerLiteral() {
    val statement = EqualsExpression(
      BooleanExpressionProperty(SQLProperty("postalcode")),
      BooleanExpressionLiteral(SQLNumberLiteral(12345))
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("The postalcode equals to 12345.", realisedSentence)
  }
}