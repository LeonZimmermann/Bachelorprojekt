package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.features.Feature
import simplenlg.framework.LexicalCategory
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

class CountProperty(propertyName: String, propertyStem: String? = null) :
  SQLProperty(propertyName, propertyStem, true) {
  override fun toSQLString(): String = "COUNT(${super.toSQLString()})"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val countPreposition = nlgFactory.createPrepositionPhrase("the number of")
    val nounPhrase = nlgFactory.createNounPhrase(super.toStemText(nlgFactory))
    nounPhrase.addPreModifier(countPreposition)
    return nounPhrase
  }

}