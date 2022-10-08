package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules

import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryResult

interface AssignmentValidationRule {
  fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String>
}