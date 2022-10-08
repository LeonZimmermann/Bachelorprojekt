package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.CountProperty
import org.junit.Assert.*
import org.junit.Test

class CountPropertyUnitTest: AbstractSQLUnitTest() {
  @Test
  fun testToStemText() {
    val property = CountProperty("street")
    val realisedSentence = realiser.realiseSentence(property.toStemText(nlgFactory))
    assertEquals("The number of streets.", realisedSentence)
    assertEquals("COUNT(street)", property.toSQLString())
  }
}