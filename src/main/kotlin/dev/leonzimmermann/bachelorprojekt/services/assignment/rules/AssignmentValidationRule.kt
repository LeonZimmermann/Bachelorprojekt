package dev.leonzimmermann.bachelorprojekt.services.assignment.rules

import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

interface AssignmentValidationRule {
  fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String>
}