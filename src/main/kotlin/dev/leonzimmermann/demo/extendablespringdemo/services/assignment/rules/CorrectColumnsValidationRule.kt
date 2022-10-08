package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules

import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryResult

object CorrectColumnsValidationRule: AssignmentValidationRule {
  override fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String> {
    TODO()
  }
}