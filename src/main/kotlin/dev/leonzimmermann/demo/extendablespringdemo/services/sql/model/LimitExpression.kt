package dev.leonzimmermann.demo.extendablespringdemo.services.sql.model

import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

class LimitExpression(private val count: Int): SQLExpression() {
  override fun toSQLString(): String = "LIMIT $count"

  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    nlgFactory.createSentence("the number of elements should be limited to $count")
}