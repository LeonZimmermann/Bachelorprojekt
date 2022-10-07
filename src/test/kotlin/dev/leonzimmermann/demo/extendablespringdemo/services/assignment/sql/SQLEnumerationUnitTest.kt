package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import org.junit.Assert.*
import org.junit.Test

class SQLEnumerationUnitTest: AbstractSQLUnitTest() {

  @Test
  fun testToStemTextWithOneProperty() {
    val sqlEnumeration = SQLEnumeration(SQLProperty("street"))
    val realisedSentence = realiser.realiseSentence(sqlEnumeration.toStemText(nlgFactory))
    assertEquals("Street.", realisedSentence)
  }

  @Test
  fun testToStemTextWithTwoProperties() {
    val sqlEnumeration = SQLEnumeration(SQLProperty("street"), SQLProperty("postalcode"))
    val realisedSentence = realiser.realiseSentence(sqlEnumeration.toStemText(nlgFactory))
    assertEquals("Street, postalcode.", realisedSentence)
  }

  @Test
  fun testToStemTextWithThreeProperties() {
    val sqlEnumeration = SQLEnumeration(SQLProperty("street"), SQLProperty("postalcode"), SQLProperty("city"))
    val realisedSentence = realiser.realiseSentence(sqlEnumeration.toStemText(nlgFactory))
    assertEquals("Street, postalcode, city.", realisedSentence)
  }
}