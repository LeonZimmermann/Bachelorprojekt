package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

class SQLLiteral(private val literalName: String) : SQLElement {
  override fun toSQLString(): String = literalName
  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    nlgFactory.createStringElement(literalName)

}