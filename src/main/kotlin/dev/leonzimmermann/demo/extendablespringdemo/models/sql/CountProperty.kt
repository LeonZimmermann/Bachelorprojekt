package dev.leonzimmermann.demo.extendablespringdemo.models.sql

class CountProperty(propertyName: String): SQLProperty(propertyName) {
  override fun toSQLString(): String = "COUNT($propertyName)"
}