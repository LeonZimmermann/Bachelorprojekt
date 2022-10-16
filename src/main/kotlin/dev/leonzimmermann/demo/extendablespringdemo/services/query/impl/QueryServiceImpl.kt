package dev.leonzimmermann.demo.extendablespringdemo.services.query.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryResult
import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.Tuple

@Service
class QueryServiceImpl(
  @Autowired private val entityManager: EntityManager
) : QueryService {

  private val logger = LoggerFactory.getLogger(javaClass.name)

  override fun executeQuery(queryString: String): QueryResult {
    logger.debug("Executing query: $queryString")
    val columns = entityManager.createNativeQuery(queryString, Tuple::class.java).resultList[0]
    val data = entityManager.createQuery(queryString).resultList
    val result = QueryResult(columns, data)
    logger.debug("Result of query is: $result")
    return result
  }
}