package dev.leonzimmermann.bachelorprojekt.assignment.impl

import dev.leonzimmermann.bachelorprojekt.assignment.*
import dev.leonzimmermann.bachelorprojekt.assignment.impl.rules.NumberOfRowsValidationRule
import dev.leonzimmermann.bachelorprojekt.assignment.impl.rules.ResultIsTheSameValidationRule
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import simplenlg.framework.NLGFactory
import simplenlg.lexicon.Lexicon
import simplenlg.realiser.english.Realiser
import javax.transaction.Transactional

/**
 * The AssignmentService manages the interaction with a student. It creates a database and assignments
 * that correspond to the created database. The user can give a solution for an assignment that is
 * then validated by the AssignmentService.
 */
@Service
internal class AssignmentServiceImpl(
    private val generationDataReader: GenerationDataReader,
    private val sqlService: SQLService,
    private val queryService: QueryService
) : AssignmentService {

    private val logger = LoggerFactory.getLogger(javaClass.name)

    private var counter = 0L
    private val listOfAssignments = mutableMapOf<Long, Assignment>()

    private val lexicon = Lexicon.getDefaultLexicon()
    private val nlgFactory = NLGFactory(lexicon)
    private val realiser = Realiser(lexicon)

    override fun setupDatabase(fileName: String) {
        queryService.executeQuery("DROP DATABASE AssignmentDatabase")
        queryService.executeQuery("CREATE DATABASE AssignmentDatabase")
        runBlocking { generationDataReader.loadSQLFromDisk(fileName) }
            .forEach { queryService.executeQuery(it) }
    }

    override fun generateNewAssignment(
        fileName: String,
        generationOptions: GenerationOptions
    ): Assignment {
        val databaseScheme = runBlocking {
            generationDataReader.loadDatabaseSchemeFromDisk(fileName)
        }
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
