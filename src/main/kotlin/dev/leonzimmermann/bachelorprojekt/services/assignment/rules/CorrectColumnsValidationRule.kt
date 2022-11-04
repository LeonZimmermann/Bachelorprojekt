package dev.leonzimmermann.bachelorprojekt.services.assignment.rules

import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

object CorrectColumnsValidationRule: AssignmentValidationRule {
  override fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String> {
    TODO()
  }
}