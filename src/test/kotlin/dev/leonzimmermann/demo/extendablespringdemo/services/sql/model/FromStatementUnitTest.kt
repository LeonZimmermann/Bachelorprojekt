package dev.leonzimmermann.demo.extendablespringdemo.services.sql.model

import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.FromStatement
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.SQLBasicLiteral
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.SQLTable
import org.junit.Test

import org.junit.Assert.*

class FromStatementUnitTest {

  @Test
  fun testToSQLString() {
    val statementWithoutAlias = FromStatement(SQLTable("Address"))
    assertEquals("FROM Address", statementWithoutAlias.toSQLString())

    val statementWithAlias = FromStatement(SQLTable("Address"), SQLBasicLiteral("a"))
    assertEquals("FROM Address AS a", statementWithAlias.toSQLString())
  }

}