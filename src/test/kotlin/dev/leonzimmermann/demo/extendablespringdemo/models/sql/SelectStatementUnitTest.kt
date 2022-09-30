package dev.leonzimmermann.demo.extendablespringdemo.models.sql

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql.*
import org.junit.Assert.assertEquals
import org.junit.Test

class SelectStatementUnitTest {

  @Test
  fun testToSQLString() {
    val selectStatement = SelectStatement(
      selectProperties = arrayOf(SQLProperty("street")),
      tableStatement = TableStatement("Address"),
      whereClause = WhereClause(EqualsExpression(
        BooleanExpressionProperty(SQLProperty("city")),
        BooleanExpressionLiteral("'Essen'")))
    )
    assertEquals("SELECT street FROM Address WHERE city='Essen'", selectStatement.toSQLString())
  }
}