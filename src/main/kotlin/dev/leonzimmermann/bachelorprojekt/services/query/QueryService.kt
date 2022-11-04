package dev.leonzimmermann.bachelorprojekt.services.query

interface QueryService {
  fun executeQuery(queryString: String): QueryResult
}