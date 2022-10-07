package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import org.junit.Assert.assertEquals
import org.junit.Test

class SelectStatementUnitTest : AbstractSQLUnitTest() {

  @Test
  fun testToSQL() {
    val statement = SelectStatement(
      selectProperties = SQLEnumeration(SQLProperty("street", plural = true)),
      fromStatement = FromStatement(SQLTable("Address"))
    )
    assertEquals("SELECT street FROM Address", statement.toSQLString().trim())
  }

  @Test
  fun testToStemText() {
    val statement = SelectStatement(
      selectProperties = SQLEnumeration(SQLProperty("street", plural = true)),
      fromStatement = FromStatement(SQLTable("Address"))
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals("Query all streets!", realisedSentence)
  }

  @Test
  fun testToSQLWithWhereClause() {
    val statement = SelectStatement(
      selectProperties = SQLEnumeration(SQLProperty("street", plural = true)),
      fromStatement = FromStatement(SQLTable("Address")),
      whereClause = WhereClause(
        EqualsExpression(
          BooleanExpressionProperty(SQLProperty("city")),
          BooleanExpressionLiteral(SQLStringLiteral("Essen"))
        )
      )
    )
    assertEquals("SELECT street FROM Address WHERE city='Essen'", statement.toSQLString().trim())
  }

  @Test
  fun testToStemTextWithWhereClause() {
    val statement = SelectStatement(
      selectProperties = SQLEnumeration(SQLProperty("postalcode", plural = true)),
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
      "Query all postalcodes where city is Essen and the number of streets is greater than 200!",
      realisedSentence
    )
    assertEquals(
      "SELECT postalcode FROM Address WHERE (city='Essen') AND (COUNT(street)>200)",
      statement.toSQLString()
    )
  }
}