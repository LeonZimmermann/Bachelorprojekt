package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.Assignment
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.AssignmentService
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules.NumberOfRowsValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.rules.ResultIsTheSameValidationRule
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryService
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.GenerationOptions
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.SQLService
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.SelectStatement
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import simplenlg.framework.NLGFactory
import simplenlg.lexicon.Lexicon
import simplenlg.realiser.english.Realiser
import kotlin.random.Random

@Service
class AssignmentServiceImpl(
  @Autowired private val sqlService: SQLService,
  @Autowired private val queryService: QueryService
) : AssignmentService {

  private val logger = LoggerFactory.getLogger(javaClass.name)

  private var counter = 0L
  private val listOfAssignments = mutableMapOf<Long, Assignment>()

  private val lexicon = Lexicon.getDefaultLexicon()
  private val nlgFactory = NLGFactory(lexicon)
  private val realiser = Realiser(lexicon)

  override fun generateNewAssignment(): Assignment {
    val databaseScheme = DatabaseScheme(arrayOf())
    val generationOptions = GenerationOptions(Random(1000), IntRange(1, 5))
    val sqlExpression = sqlService.generateSQLExpression(databaseScheme, generationOptions)
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