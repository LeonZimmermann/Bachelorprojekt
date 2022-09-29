package dev.leonzimmermann.demo.extendablespringdemo.models.sql

interface SQLElement {
  fun toSQLString(): String
}