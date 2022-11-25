package dev.leonzimmermann.bachelorprojekt.usecases.assignment.rules

import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

object NumberOfRowsValidationRule: AssignmentValidationRule {
  override fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String> {
    return if (solutionResult.data.size != usersResult.data.size) {
      listOf("${usersResult.data.size} rows were retrieved, but the correct number of rows is ${solutionResult.data.size}")
    } else {
      emptyList()
    }
  }
}