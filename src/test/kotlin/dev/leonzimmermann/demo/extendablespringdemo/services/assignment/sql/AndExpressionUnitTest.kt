package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.*
import org.junit.Assert.*
import org.junit.Test

class AndExpressionUnitTest : AbstractSQLUnitTest() {
  @Test
  fun testToStemTextWithTwoProperties() {
    val statement = AndExpression(
      BooleanExpressionProperty(SQLProperty("street")),
      BooleanExpressionProperty(SQLProperty("city"))
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("The street and the city.", realisedSentence)
  }

  @Test
  fun testToStemTextWithTwoComparisons() {
    val statement = AndExpression(
      EqualsExpression(
        BooleanExpressionProperty(SQLProperty("city")),
        BooleanExpressionLiteral(SQLStringLiteral("'Essen'"))
      ),
      NotEqualsExpression(
        BooleanExpressionProperty(SQLProperty("postalcode")),
        BooleanExpressionLiteral(SQLNumberLiteral(12345))
      )
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("The city equals to 'Essen' and the postalcode does not equal 12345.", realisedSentence)
  }
}