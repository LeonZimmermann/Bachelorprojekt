package dev.leonzimmermann.bachelorprojekt.services.sql.model

import dev.leonzimmermann.bachelorprojekt.services.sql.model.LimitExpression
import org.junit.Assert.assertEquals
import org.junit.Test

class LimitExpressionUnitTest : AbstractSQLUnitTest() {

  @Test
  fun testLimit10() {
    val expression = LimitExpression(10)
    val realisedSentence = realiser.realiseSentence(expression.toStemText(nlgFactory))
    assertEquals("The number of elements should be limited to 10.", realisedSentence)
    assertEquals("LIMIT 10", expression.toSQLString())
  }

}