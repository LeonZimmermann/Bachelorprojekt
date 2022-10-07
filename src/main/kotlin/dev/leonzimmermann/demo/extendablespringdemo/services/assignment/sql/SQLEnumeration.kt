package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.features.Feature
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

class SQLEnumeration<T : SQLElement>(private vararg val elements: T) : SQLElement {
  override fun toSQLString(): String = elements.joinToString(", ") { it.toSQLString() }
  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val list = nlgFactory.createCoordinatedPhrase()
    elements.forEach {
      list.addCoordinate(it.toStemText(nlgFactory))
      list.setFeature(Feature.CONJUNCTION, ",")
    }
    return list
  }
}