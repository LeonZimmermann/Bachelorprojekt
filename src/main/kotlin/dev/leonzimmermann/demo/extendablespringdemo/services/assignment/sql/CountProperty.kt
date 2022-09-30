package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

class CountProperty(propertyName: String, propertyStem: String? = null) :
  SQLProperty(propertyName, propertyStem) {
  override fun toSQLString(): String = "COUNT(${super.toSQLString()})"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    TODO("Implement")
  }

}