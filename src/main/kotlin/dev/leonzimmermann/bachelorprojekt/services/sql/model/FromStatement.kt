package dev.leonzimmermann.bachelorprojekt.services.sql.model

import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

class FromStatement(private val tableName: SQLTable, private val tableAlias: SQLLiteral? = null) :
  SQLElement {
  override fun toSQLString(): String {
    var result = "FROM ${tableName.toSQLString()}"
    tableAlias?.let {
      result += " AS ${it.toSQLString()}"
    }
    return result
  }

  override fun toStemText(nlgFactory: NLGFactory): NLGElement =
    nlgFactory.createAdverbPhrase("from the table $tableName")
}
