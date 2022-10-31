package dev.leonzimmermann.demo.extendablespringdemo.services.sql.model

import org.junit.Assert.assertEquals
import org.junit.Test

class  SelectStatementUnitTest : AbstractSQLUnitTest() {

  @Test
  fun testToSQLString() {
    val selectStatement = SelectStatement(
      selectProperties = SQLEnumeration(SQLProperty("street")),
      fromStatement = FromStatement(SQLTable("Address")),
      whereClause = WhereClause(EqualsExpression(
        BooleanExpressionProperty(SQLProperty("city")),
        BooleanExpressionLiteral(SQLStringLiteral("Essen"))))
    )
    assertEquals("SELECT street FROM Address WHERE city='Essen'", selectStatement.toSQLString())
  }

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
  fun testTextWithWhereClause() {
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
      """
        SELECT postalcode FROM Address
        WHERE (city='Essen') AND (COUNT(street)>200)
      """.trimIndent(),
      statement.toSQLString().trim()
    )
  }

  @Test
  fun testWithWhereClauseAndLimit() {
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
      """
        SELECT postalcode FROM Address
        WHERE city='Essen'
        LIMIT 3
      """.trimIndent(),
      statement.toSQLString().trim()
    )
  }

  @Test
  fun testWithJoins() {
    val statement = SelectStatement(
      selectProperties = SQLEnumeration(
        SQLProperty(
          "lastname",
          "last name",
          plural = true,
          possessor = SQLTable("Person")
        )
      ),
      fromStatement = FromStatement(SQLTable("Person")),
      joinExpressions = arrayOf(
        JoinExpression(
          SQLTable("Address"),
          SQLTable("Person"),
          SQLProperty("id"),
          SQLProperty("address")
        )
      ),
      whereClause = WhereClause(
        EqualsExpression(
          BooleanExpressionProperty(SQLProperty("city")),
          BooleanExpressionLiteral(SQLStringLiteral("Essen")),
          "be"
        )
      ),
    )
    val realisedSentence = realiser.realiseSentence(statement.toStemText(nlgFactory))
    assertEquals(
      "Query all the Person's last names where the city is Essen.",
      realisedSentence.trim()
    )
    assertEquals(
      """
      SELECT lastname FROM Person
      JOIN Address ON Address.id=Person.address
      WHERE city='Essen'
    """.trimIndent(), statement.toSQLString().trim()
    )
  }
}