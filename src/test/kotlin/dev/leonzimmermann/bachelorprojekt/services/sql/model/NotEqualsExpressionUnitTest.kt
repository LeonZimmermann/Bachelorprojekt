package dev.leonzimmermann.bachelorprojekt.services.sql.model

import dev.leonzimmermann.bachelorprojekt.services.sql.model.*
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
    assertEquals("The city does not equal 'Essen'.", realisedSentence)
  }

  @Test
  fun testToStemTextWithAPropertiesALiteralAndARelationWord() {
    val statement = NotEqualsExpression(
      BooleanExpressionProperty(SQLProperty("city")),
      BooleanExpressionLiteral(SQLStringLiteral("'Essen'")),
      "is"
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("The city is not 'Essen'.", realisedSentence)
  }

  @Test
  fun testToStemTextWithAPropertiesAndAnIntegerLiteral() {
    val statement = NotEqualsExpression(
      BooleanExpressionProperty(SQLProperty("postalcode")),
      BooleanExpressionLiteral(SQLNumberLiteral(12345))
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("The postalcode does not equal 12345.", realisedSentence)
  }
}