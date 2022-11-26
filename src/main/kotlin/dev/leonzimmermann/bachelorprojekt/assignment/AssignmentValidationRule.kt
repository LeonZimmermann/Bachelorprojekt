package dev.leonzimmermann.bachelorprojekt.assignment

import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

interface AssignmentValidationRule {
  fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String>
}