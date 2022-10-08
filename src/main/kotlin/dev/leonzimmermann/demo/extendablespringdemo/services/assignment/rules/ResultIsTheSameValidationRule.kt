package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules

import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryResult

object ResultIsTheSameValidationRule: AssignmentValidationRule {
  override fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String> {
    return if (solutionResult != usersResult) {
      listOf("The result is not correct")
    } else {
      emptyList()
    }
  }
}