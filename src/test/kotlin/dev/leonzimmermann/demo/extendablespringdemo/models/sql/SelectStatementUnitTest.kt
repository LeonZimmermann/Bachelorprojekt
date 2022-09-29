package dev.leonzimmermann.demo.extendablespringdemo.models.sql

import org.junit.Assert.assertEquals
import org.junit.Test

class SelectStatementUnitTest {

  @Test
  fun testToSQLString() {
    val selectStatement = SelectStatement(
      selectProperties = arrayOf(BasicProperty("street")),
      tableStatement = TableStatement("Address"),
      whereClause = WhereClause(BooleanExpression("city = 'Essen'"))
    )
    assertEquals("SELECT street FROM Address WHERE city = 'Essen'", selectStatement.toSQLString())
  }
}