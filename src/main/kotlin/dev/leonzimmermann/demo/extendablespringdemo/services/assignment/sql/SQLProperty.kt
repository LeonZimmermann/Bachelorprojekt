package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import ch.qos.logback.core.util.AggregationType
import gov.nih.nlm.nls.lvg.Lib.Gender
import simplenlg.features.Feature
import simplenlg.features.LexicalFeature
import simplenlg.framework.LexicalCategory
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

open class SQLProperty(
  private val propertyName: String,
  private val propertyStem: String? = null,
  private val plural: Boolean = false
) :
  SQLElement {
  override fun toSQLString(): String = propertyName
  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val word = nlgFactory.createWord(propertyStem ?: propertyName, LexicalCategory.NOUN)
    word.isPlural = plural
    return word
  }
}