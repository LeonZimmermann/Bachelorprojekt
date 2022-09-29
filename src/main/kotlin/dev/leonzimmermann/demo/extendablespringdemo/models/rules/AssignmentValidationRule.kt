package dev.leonzimmermann.demo.extendablespringdemo.models.rules

interface AssignmentValidationRule {
  fun validate(solutionResult: List<Any?>, usersResult: List<Any?>): List<String>
}