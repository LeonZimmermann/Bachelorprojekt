package dev.leonzimmermann.demo.extendablespringdemo.services.sql.model

import org.junit.Assert.*
import org.junit.Test

class SQLPropertyUnitTest: AbstractSQLUnitTest() {

  @Test
  fun testToStemText() {
    val property = SQLProperty("postalcode")
    val realisedSentence = realiser.realiseSentence(property.toStemText(nlgFactory))
    assertEquals("The postalcode.", realisedSentence.trim())
  }

  @Test
  fun testToStemTextWithPropertyStem() {
    val property = SQLProperty("postal_code", "postalcode")
    val realisedSentence = realiser.realiseSentence(property.toStemText(nlgFactory))
    assertEquals("The postalcode.", realisedSentence.trim())
  }

  @Test
  fun testToStemTextNoSpecifier() {
    val property = SQLProperty("postalcode", withSpecifier = false)
    val realisedSentence = realiser.realiseSentence(property.toStemText(nlgFactory))
    assertEquals("Postalcode.", realisedSentence.trim())
  }

  @Test
  fun testToStemTextPlural() {
    val property = SQLProperty("postalcode", plural = true)
    val realisedSentence = realiser.realiseSentence(property.toStemText(nlgFactory))
    assertEquals("The postalcodes.", realisedSentence.trim())
  }

  @Test
  fun testToStemTextWithPossessor() {
    val property = SQLProperty("postalcode", possessor = SQLTable("person"))
    val realisedSentence = realiser.realiseSentence(property.toStemText(nlgFactory))
    assertEquals("The person's postalcode.", realisedSentence.trim())
  }

}