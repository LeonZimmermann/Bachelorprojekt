package dev.leonzimmermann.demo.extendablespringdemo.models.sql

import org.junit.Test

import org.junit.Assert.*

class TableStatementUnitTest {

  @Test
  fun testToSQLString() {
    val statementWithoutAlias = TableStatement("Address")
    assertEquals("FROM Address", statementWithoutAlias.toSQLString())

    val statementWithAlias = TableStatement("Address", "a")
    assertEquals("FROM Address as a", statementWithAlias.toSQLString())
  }

}