package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import org.junit.Assert.assertEquals
import org.junit.Test

class SelectStatementUnitTest : AbstractSQLUnitTest() {

  @Test
  fun testWithoutWhereClause() {
    val statement = SelectStatement(
      selectProperties = SQLPropertyEnumeration(Pair("street", null)),
      fromStatement = FromStatement(SQLTable("Address"))
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("Query all the streets.", realisedSentence.trim())
    assertEquals("SELECT street FROM Address", statement.toSQLString().trim())
  }

  @Test
  fun testToStemTextWithWhereClause() {
    val statement = SelectStatement(
      selectProperties = SQLPropertyEnumeration(Pair("postalcode", null)),
      fromStatement = FromStatement(SQLTable("Address")),
      whereClause = WhereClause(
        AndExpression(
          EqualsExpression(
            BooleanExpressionProperty(SQLProperty("city")),
            BooleanExpressionLiteral(SQLStringLiteral("Essen")),
            "be"
          ),
          GreaterExpression(
            BooleanExpressionProperty(CountProperty("street")),
            BooleanExpressionLiteral(SQLNumberLiteral(200))
          ),
        )
      )
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals(
      "Query all the postalcodes where the city is Essen and the number of streets are greater than 200.",
      realisedSentence.trim()
    )
    assertEquals(
      "SELECT postalcode FROM Address WHERE (city='Essen') AND (COUNT(street)>200)",
      statement.toSQLString().trim()
    )
  }

  @Test
  fun testToStemTextWithWhereClauseAndLimit() {
    val statement = SelectStatement(
      selectProperties = SQLPropertyEnumeration(Pair("postalcode", null)),
      fromStatement = FromStatement(SQLTable("Address")),
      whereClause = WhereClause(
        EqualsExpression(
          BooleanExpressionProperty(SQLProperty("city")),
          BooleanExpressionLiteral(SQLStringLiteral("Essen")),
          "be"
        )
      ),
      limitExpression = LimitExpression(3)
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals(
      "Query all the postalcodes where the city is Essen. The number of elements should be limited to 3.",
      realisedSentence.trim()
    )
    assertEquals(
      "SELECT postalcode FROM Address WHERE city='Essen' LIMIT 3",
      statement.toSQLString().trim()
    )
  }
}