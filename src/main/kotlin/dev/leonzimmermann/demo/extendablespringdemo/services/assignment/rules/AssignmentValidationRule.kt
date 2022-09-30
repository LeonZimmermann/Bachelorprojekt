package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules

interface AssignmentValidationRule {
  fun validate(solutionResult: List<Any?>, usersResult: List<Any?>): List<String>
}