package dev.leonzimmermann.demo.extendablespringdemo.services.impl

import dev.leonzimmermann.demo.extendablespringdemo.models.Assignment
import dev.leonzimmermann.demo.extendablespringdemo.services.AssignmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AssignmentServiceImpl: AssignmentService {

  @Autowired
  private lateinit var entityManager: EntityManager

  val assigment = Assignment(
    0, "Get all streets in the city of Essen", """
      SELECT street FROM address
      WHERE address.city = "Essen"
    """.trimIndent()
  )

  override fun generateNewAssignment(): Assignment {
    return assigment
  }

  override fun solveAssignmentAndReturnListOfDiscrepancies(objectId: Int, answer: String): List<String> {
    val solutionResult = executeQuery(assigment.solution)
    val usersResult = executeQuery(answer)
    return getDiscrepanciesBetweenResultSets(solutionResult, usersResult)
  }

  private fun executeQuery(queryString: String): List<Any> {
    // TODO Implement
    /*
    val query = entityManager.createQuery(queryString)
    return query.resultList
     */
    return emptyList()
  }

  private fun getDiscrepanciesBetweenResultSets(
    solutionResult: List<Any>,
    usersResult: List<Any>
  ): List<String> {
    // TODO Implement
    return emptyList()
  }
}