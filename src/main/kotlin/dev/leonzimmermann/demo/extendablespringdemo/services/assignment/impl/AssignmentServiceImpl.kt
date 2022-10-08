package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.Assignment
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.AssignmentService
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules.NumberOfRowsValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules.ResultIsTheSameValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryService
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.SQLService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import simplenlg.framework.NLGFactory
import simplenlg.lexicon.Lexicon
import simplenlg.realiser.english.Realiser

@Service
class AssignmentServiceImpl : AssignmentService {

  private val logger = LoggerFactory.getLogger(javaClass.name)

  @Autowired
  private lateinit var sqlService: SQLService
  @Autowired
  private lateinit var queryService: QueryService

  private var counter = 0L
  private val listOfAssignments = mutableMapOf<Long, Assignment>()

  private val lexicon = Lexicon.getDefaultLexicon()
  private val nlgFactory = NLGFactory(lexicon)
  private val realiser = Realiser(lexicon)

  override fun generateNewAssignment(): Assignment {
    val sqlExpression = sqlService.generateSQLExpression()
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

  override fun validateSolution(objectId: Long, answer: String): List<String> {
    logger.debug("Validating solution for assignment with objectId $objectId")
    val assignment = listOfAssignments[objectId]
      ?: throw IllegalArgumentException("Could not find objectId $objectId")
    val solutionResult = queryService.executeQuery(assignment.sqlExpression.toSQLString())
    val usersResult = queryService.executeQuery(answer)
    return assignment.validationRules.flatMap { it.validate(solutionResult, usersResult) }
  }
}