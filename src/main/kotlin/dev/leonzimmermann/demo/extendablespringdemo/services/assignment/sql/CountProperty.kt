package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

class CountProperty(propertyName: String): SQLProperty(propertyName) {
  override fun toSQLString(): String = "COUNT($propertyName)"
}