package dev.leonzimmermann.bachelorprojekt.assignment.impl.rules

import dev.leonzimmermann.bachelorprojekt.assignment.AssignmentValidationRule
import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

internal object CorrectColumnsValidationRule: AssignmentValidationRule {
  override fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String> {
    TODO()
  }
}
