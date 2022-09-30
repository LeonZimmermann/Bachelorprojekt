package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

class SQLEnumeration<T : SQLElement>(private vararg val elements: T) : SQLElement {
  override fun toSQLString(): String = elements.joinToString(", ") { it.toSQLString() }
  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val list = nlgFactory.createList()
    elements.forEach {
      list.addComponent(it.toStemText(nlgFactory))
    }
    return list
  }
}