package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.features.Feature
import simplenlg.features.LexicalFeature
import simplenlg.framework.LexicalCategory
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

open class SQLProperty(
  private val propertyName: String,
  private val propertyStem: String? = null,
  private val plural: Boolean = false,
  private val withSpecifier: Boolean = true,
  private val possessor: SQLElement? = null
) :
  SQLElement {
  override fun toSQLString(): String = propertyName
  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val word = nlgFactory.createWord(propertyStem ?: propertyName, LexicalCategory.NOUN)
    if (possessor != null) {
      word.setFeature(LexicalFeature.PLURAL, plural)
    }
    val propertyPhrase = nlgFactory.createNounPhrase(word)
    val returningPhrase = if (possessor != null) {
      val tableStemText = possessor.toStemText(nlgFactory)
      tableStemText.setFeature(LexicalFeature.PLURAL, plural)
      val tablePhrase = nlgFactory.createNounPhrase(tableStemText)
      tablePhrase.setFeature(Feature.POSSESSIVE, true)
      tablePhrase.addComplement(propertyPhrase)
      tablePhrase
    } else {
      propertyPhrase
    }
    if (withSpecifier) {
      returningPhrase.setSpecifier("the")
    }
    return returningPhrase
  }
}