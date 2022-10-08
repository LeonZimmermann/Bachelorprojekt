package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.Assignment
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.AssignmentService
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules.NumberOfRowsValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules.ResultIsTheSameValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.sql.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import simplenlg.framework.NLGFactory
import simplenlg.lexicon.Lexicon
import simplenlg.realiser.english.Realiser
import javax.persistence.EntityManager

@Service
class AssignmentServiceImpl : AssignmentService {

  private val logger = LoggerFactory.getLogger(javaClass.name)

  private var counter = 0L
  private val listOfAssignments = mutableMapOf<Long, Assignment>()

  @Autowired
  private lateinit var entityManager: EntityManager

  private val lexicon = Lexicon.getDefaultLexicon()
  private val nlgFactory = NLGFactory(lexicon)
  private val realiser = Realiser(lexicon)

  override fun generateNewAssignment(): Assignment {
    val sqlExpression = SelectStatement(
      selectProperties = SQLEnumeration(SQLProperty("street")),
      fromStatement = FromStatement(SQLTable("Address")),
      whereClause = WhereClause(
        EqualsExpression(
          BooleanExpressionProperty(SQLProperty("city")),
          BooleanExpressionLiteral(SQLStringLiteral("Essen"))
        )
      )
    )
    val stem = realiser.realiseSentence(sqlExpression.toStemText(nlgFactory)).trim()
    val assignment = Assignment(
      stem = stem,
      sqlExpression = sqlExpression,
      validationRules = arrayOf(ResultIsTheSameValidationRule, NumberOfRowsValidationRule)
    )
    logger.debug("Generated new assignment: $assignment")
    listOfAssignments[counter++] = assignment
    return assignment
  }

  override fun solveAssignmentAndReturnListOfDiscrepancies(
    objectId: Long, answer: String
  ): List<String> {
    logger.debug("Solving assignment with objectId $objectId")
    val assignment = listOfAssignments[objectId]
      ?: throw IllegalArgumentException("Could not find objectId $objectId")
    val solutionResult = executeQuery(assignment.sqlExpression.toSQLString())
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