package dev.leonzimmermann.bachelorprojekt.services.sql.model

import org.junit.Assert.*
import org.junit.Test

class RangeExpressionUnitTest: AbstractSQLUnitTest() {
    @Test
    fun testToStemTextWithAPropertiesAndALiteral() {
        val statement = RangeExpression(
            BooleanExpressionProperty(SQLProperty("size")),
            SQLNumberLiteral("100"),
            SQLNumberLiteral("500")
        )
        val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
        assertEquals("The size is between 100 and 500.", realisedSentence)
    }
}
