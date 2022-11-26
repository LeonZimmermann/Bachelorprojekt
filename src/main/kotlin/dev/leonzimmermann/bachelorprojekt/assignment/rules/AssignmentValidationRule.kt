package dev.leonzimmermann.bachelorprojekt.assignment.rules

import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

interface AssignmentValidationRule {
  fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String>
}