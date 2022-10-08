package dev.leonzimmermann.demo.extendablespringdemo.services.query.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class QueryServiceImpl : QueryService {

  private val logger = LoggerFactory.getLogger(javaClass.name)

  @Autowired
  private lateinit var entityManager: EntityManager

  override fun executeQuery(queryString: String): List<Any?> {
    logger.debug("Executing query: $queryString")
    val query = entityManager.createQuery(queryString)
    val result = query.resultList
    logger.debug("Result of query is: $result")
    return result.toList()
  }
}