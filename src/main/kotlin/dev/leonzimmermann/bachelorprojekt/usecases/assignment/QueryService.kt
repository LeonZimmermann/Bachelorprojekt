package dev.leonzimmermann.bachelorprojekt.usecases.assignment

import dev.leonzimmermann.bachelorprojekt.services.query.QueryResult

interface QueryService {
  fun executeQuery(queryString: String): QueryResult
}