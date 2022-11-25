package dev.leonzimmermann.bachelorprojekt.usecases.assignment

import dev.leonzimmermann.bachelorprojekt.usecases.assignment.rules.AssignmentValidationRule
import dev.leonzimmermann.bachelorprojekt.services.sql.model.SQLExpression

class Assignment(
  val stem: String,
  val sqlExpression: SQLExpression,
  val validationRules: Array<AssignmentValidationRule>
) {
  val solution = sqlExpression.toSQLString().trim()
  override fun toString(): String {
    return "Assignment(stem='$stem', solution='$solution')"
  }

}