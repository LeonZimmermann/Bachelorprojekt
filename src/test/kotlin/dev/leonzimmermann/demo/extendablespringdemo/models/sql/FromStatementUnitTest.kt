package dev.leonzimmermann.demo.extendablespringdemo.models.sql

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql.FromStatement
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql.SQLLiteral
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql.SQLTable
import org.junit.Test

import org.junit.Assert.*

class FromStatementUnitTest {

  @Test
  fun testToSQLString() {
    val statementWithoutAlias = FromStatement(SQLTable("Address"))
    assertEquals("FROM Address", statementWithoutAlias.toSQLString())

    val statementWithAlias = FromStatement(SQLTable("Address"), SQLLiteral("a"))
    assertEquals("FROM Address AS a", statementWithAlias.toSQLString())
  }

}