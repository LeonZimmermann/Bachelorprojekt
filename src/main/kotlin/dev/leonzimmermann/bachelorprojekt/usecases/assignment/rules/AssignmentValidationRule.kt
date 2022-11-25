package dev.leonzimmermann.bachelorprojekt.usecases.assignment.rules

import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

interface AssignmentValidationRule {
  fun validate(solutionResult: QueryResult, usersResult: QueryResult): List<String>
}