package dev.leonzimmermann.demo.extendablespringdemo.models.fixedDatabase

import dev.leonzimmermann.demo.extendablespringdemo.models.rules.AssignmentValidationRule

data class Assignment(
  val stem: String,
  val solution: String,
  val validationRules: Array<AssignmentValidationRule>
)