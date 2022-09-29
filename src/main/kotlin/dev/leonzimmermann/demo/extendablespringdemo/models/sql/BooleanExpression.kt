package dev.leonzimmermann.demo.extendablespringdemo.models.sql

class BooleanExpression(private val expression: String): SQLElement {
  override fun toSQLString(): String = expression
}
