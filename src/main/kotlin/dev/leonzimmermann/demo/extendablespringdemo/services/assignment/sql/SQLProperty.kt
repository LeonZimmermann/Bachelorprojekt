package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.framework.LexicalCategory
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

open class SQLProperty(private val propertyName: String, private val propertyStem: String? = null) :
  SQLElement {
  override fun toSQLString(): String = propertyName
  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    nlgFactory.createWord(propertyStem ?: propertyName, LexicalCategory.NOUN)
}