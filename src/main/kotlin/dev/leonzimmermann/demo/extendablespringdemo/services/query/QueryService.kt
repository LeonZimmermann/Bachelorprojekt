package dev.leonzimmermann.demo.extendablespringdemo.services.query

interface QueryService {
  fun executeQuery(queryString: String): QueryResult
}