package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules

object ResultIsTheSameValidationRule: AssignmentValidationRule {
  override fun validate(solutionResult: List<Any?>, usersResult: List<Any?>): List<String> {
    return if (solutionResult != usersResult) {
      listOf("The result is not correct")
    } else {
      emptyList()
    }
  }
}