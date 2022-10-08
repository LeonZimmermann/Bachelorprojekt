package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.SQLPropertyEnumeration
import org.junit.Assert.*
import org.junit.Test

class SQLPropertyEnumerationUnitTest: AbstractSQLUnitTest() {

  @Test
  fun testToStemTextWithOneProperty() {
    val sqlEnumeration = SQLPropertyEnumeration(Pair("street", null))
    val realisedSentence = realiser.realiseSentence(sqlEnumeration.toStemText(nlgFactory))
    assertEquals("The streets.", realisedSentence)
  }

  @Test
  fun testToStemTextWithTwoProperties() {
    val sqlEnumeration = SQLPropertyEnumeration(Pair("street", null), Pair("postalcode", null))
    val realisedSentence = realiser.realiseSentence(sqlEnumeration.toStemText(nlgFactory))
    assertEquals("The streets and postalcodes.", realisedSentence)
  }

  @Test
  fun testToStemTextWithThreeProperties() {
    val sqlEnumeration = SQLPropertyEnumeration(Pair("street", null), Pair("postalcode", null), Pair("city", null))
    val realisedSentence = realiser.realiseSentence(sqlEnumeration.toStemText(nlgFactory))
    assertEquals("The streets, postalcodes and cities.", realisedSentence)
  }
}