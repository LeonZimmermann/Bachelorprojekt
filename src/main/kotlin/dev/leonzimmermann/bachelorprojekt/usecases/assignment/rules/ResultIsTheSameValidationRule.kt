package dev.leonzimmermann.bachelorprojekt.usecases.assignment.rules

import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

object ResultIsTheSameValidationRule: AssignmentValidationRule {
  override fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String> {
    return if (solutionResult != usersResult) {
      listOf("The result is not correct")
    } else {
      emptyList()
    }
  }
}