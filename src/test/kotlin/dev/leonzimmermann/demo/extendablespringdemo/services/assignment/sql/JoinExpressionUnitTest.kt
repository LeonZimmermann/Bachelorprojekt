package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import org.junit.Test

import org.junit.Assert.*

class JoinExpressionUnitTest {

  @Test
  fun toStemText() {
    val expression = JoinExpression(
      SQLTable("Address"),
      SQLTable("Person"),
      SQLProperty("id"),
      SQLProperty("address"),
    )
    assertEquals("JOIN Address ON Address.id=Person.address", expression.toSQLString())
  }
}