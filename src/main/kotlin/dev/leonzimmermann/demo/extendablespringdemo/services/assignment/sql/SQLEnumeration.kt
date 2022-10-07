package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.features.Feature
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

open class SQLEnumeration<T : SQLElement>(private vararg val elements: T) : SQLElement {
  override fun toSQLString(): String = elements.joinToString(", ") { it.toSQLString() }
  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val list = nlgFactory.createCoordinatedPhrase()
    elements.forEachIndexed { index, value ->
      list.addCoordinate(value.toStemText(nlgFactory))
      if (index < elements.size - 1) {
        list.setFeature(Feature.CONJUNCTION, ",")
      } else {
        list.setFeature(Feature.CONJUNCTION, "and")
      }
    }
    return list
  }
}