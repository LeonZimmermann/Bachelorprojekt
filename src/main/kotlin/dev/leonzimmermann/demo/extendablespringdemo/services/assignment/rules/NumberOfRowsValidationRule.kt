package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules

object NumberOfRowsValidationRule: AssignmentValidationRule {
  override fun validate(solutionResult: List<Any?>, usersResult: List<Any?>): List<String> {
    return if (solutionResult.size != usersResult.size) {
      listOf("${usersResult.size} rows were retrieved, but the correct number of rows is ${solutionResult.size}")
    } else {
      emptyList()
    }
  }
}