package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

sealed class SQLLiteral : SQLElement

class SQLBasicLiteral(private val literalName: String) : SQLLiteral() {
  override fun toSQLString(): String = literalName
  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    nlgFactory.createStringElement(literalName)
}

class SQLStringLiteral(private val literalName: String) : SQLLiteral() {
  override fun toSQLString(): String = "'$literalName'"
  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    nlgFactory.createStringElement(literalName)
}

class SQLNumberLiteral(private val number: Int, private val unit: String? = null) : SQLLiteral() {
  override fun toSQLString(): String {
    return number.toString()
  }

  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    return nlgFactory.createStringElement("$number${unit ?: ""}")
  }
}