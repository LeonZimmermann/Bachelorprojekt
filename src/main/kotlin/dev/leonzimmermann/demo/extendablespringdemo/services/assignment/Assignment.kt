package dev.leonzimmermann.demo.extendablespringdemo.services.assignment

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules.AssignmentValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql.SQLExpression

class Assignment(
  val stem: String,
  val solution: SQLExpression,
  val validationRules: Array<AssignmentValidationRule>
) {
  override fun toString(): String {
    return "Assignment(stem='$stem', solution=$solution, validationRules=${validationRules.contentToString()})"
  }
}