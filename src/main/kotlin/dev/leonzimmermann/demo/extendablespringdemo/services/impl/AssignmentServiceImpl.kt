package dev.leonzimmermann.demo.extendablespringdemo.services.impl

import dev.leonzimmermann.demo.extendablespringdemo.models.Assignment
import dev.leonzimmermann.demo.extendablespringdemo.repositories.AssignmentRepository
import dev.leonzimmermann.demo.extendablespringdemo.services.AssignmentService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AssignmentServiceImpl : AssignmentService {

  private val logger = LoggerFactory.getLogger(javaClass.name)

  @Autowired
  private lateinit var assignmentRepository: AssignmentRepository

  @Autowired
  private lateinit var entityManager: EntityManager

  override fun generateNewAssignment(): Assignment {
    val assignment = Assignment(
      stem = "Get all streets in the city of Essen",
      solution = """
      SELECT street FROM address
      WHERE address.city = "Essen"
    """.trimIndent()
    )
    logger.debug("Generated new assignment: $assignment")
    return assignmentRepository.save(assignment)
  }

  override fun solveAssignmentAndReturnListOfDiscrepancies(
    objectId: Long,
    answer: String
  ): List<String> {
    logger.debug("Solving assignment with objectId $objectId")
    val assignment = assignmentRepository.findById(objectId)
      .orElseThrow { IllegalArgumentException("Could not find objectId $objectId") }
    val solutionResult = executeQuery(assignment.solution)
    val usersResult = executeQuery(answer)
    return getDiscrepanciesBetweenResultSets(solutionResult, usersResult)
  }

  private fun executeQuery(queryString: String): List<Any?> {
    logger.debug("Executing query: $queryString")
    val result = entityManager.createQuery(queryString).resultList
    logger.debug("Result of query is: $result")
    return result.toList()
  }

  private fun getDiscrepanciesBetweenResultSets(
    solutionResult: List<Any?>,
    usersResult: List<Any?>
  ): List<String> {
    // TODO Implement
    return emptyList()
  }
}