package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

interface SQLElement {
  fun toSQLString(): String
}