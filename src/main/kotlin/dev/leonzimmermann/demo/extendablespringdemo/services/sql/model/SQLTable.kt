package dev.leonzimmermann.demo.extendablespringdemo.services.sql.model

import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

class SQLTable(private val tableName: String) : SQLElement {
  override fun toSQLString(): String = tableName
  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    nlgFactory.createNounPhrase(tableName)
}
