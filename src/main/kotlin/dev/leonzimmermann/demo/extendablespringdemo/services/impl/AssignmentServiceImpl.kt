package dev.leonzimmermann.demo.extendablespringdemo.services.impl

import dev.leonzimmermann.demo.extendablespringdemo.models.Assignment
import dev.leonzimmermann.demo.extendablespringdemo.models.rules.AssignmentValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.models.rules.NumberOfRowsValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.models.rules.ResultIsTheSameValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.services.AssignmentService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AssignmentServiceImpl : AssignmentService {

  private val logger = LoggerFactory.getLogger(javaClass.name)

  private var counter = 0L
  private val listOfAssignments = mutableMapOf<Long, Assignment>()

  @Autowired
  private lateinit var entityManager: EntityManager

  override fun generateNewAssignment(): Assignment {
    val assignment = Assignment(
      stem = "Get all streets in the city of Essen",
      solution = """
      SELECT DISTINCT street FROM Address
      WHERE city = 'Essen'
    """.trimIndent(),
      validationRules = arrayOf(ResultIsTheSameValidationRule, NumberOfRowsValidationRule)
    )
    logger.debug("Generated new assignment: $assignment")
    return listOfAssignments.put(counter++, assignment)!!
  }

  override fun solveAssignmentAndReturnListOfDiscrepancies(
    objectId: Long,
    answer: String
  ): List<String> {
    logger.debug("Solving assignment with objectId $objectId")
    val assignment = listOfAssignments[objectId]
      ?: throw IllegalArgumentException("Could not find objectId $objectId")
    val solutionResult = executeQuery(assignment.solution)
    val usersResult = executeQuery(answer)
    return assignment.validationRules.flatMap { it.validate(solutionResult, usersResult) }
  }

  private fun executeQuery(queryString: String): List<Any?> {
    logger.debug("Executing query: $queryString")
    val result = entityManager.createQuery(queryString).resultList
    logger.debug("Result of query is: $result")
    return result.toList()
  }
}