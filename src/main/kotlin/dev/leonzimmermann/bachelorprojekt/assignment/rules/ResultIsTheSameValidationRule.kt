package dev.leonzimmermann.bachelorprojekt.assignment.rules

import dev.leonzimmermann.bachelorprojekt.assignment.AssignmentValidationRule
import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

internal object ResultIsTheSameValidationRule: AssignmentValidationRule {
  override fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String> {
    return if (solutionResult != usersResult) {
      listOf("The result is not correct")
    } else {
      emptyList()
    }
  }
}