package dev.leonzimmermann.bachelorprojekt.services.sql.model

import dev.leonzimmermann.bachelorprojekt.services.sql.model.JoinExpression
import dev.leonzimmermann.bachelorprojekt.services.sql.model.SQLProperty
import dev.leonzimmermann.bachelorprojekt.services.sql.model.SQLTable
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