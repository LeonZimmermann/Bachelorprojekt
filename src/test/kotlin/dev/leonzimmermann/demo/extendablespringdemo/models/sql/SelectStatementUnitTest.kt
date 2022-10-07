package dev.leonzimmermann.demo.extendablespringdemo.models.sql

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql.*
import org.junit.Assert.assertEquals
import org.junit.Test

class SelectStatementUnitTest {

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
}