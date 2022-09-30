package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql

import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

interface SQLElement {
  fun toSQLString(): String
  fun toStemText(nlgFactory: NLGFactory): NLGElement
}