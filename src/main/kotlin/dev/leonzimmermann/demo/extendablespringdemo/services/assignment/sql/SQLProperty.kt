package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.framework.LexicalCategory
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

open class SQLProperty(
  private val propertyName: String,
  private val propertyStem: String? = null,
  private val plural: Boolean = false,
  private val withSpecifier: Boolean = true
) :
  SQLElement {
  override fun toSQLString(): String = propertyName
  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val word = nlgFactory.createWord(propertyStem ?: propertyName, LexicalCategory.NOUN)
    word.isPlural = plural
    val nounPhrase = nlgFactory.createNounPhrase(word)
    if (withSpecifier) {
      nounPhrase.setSpecifier("the")
    }
    return nounPhrase
  }
}