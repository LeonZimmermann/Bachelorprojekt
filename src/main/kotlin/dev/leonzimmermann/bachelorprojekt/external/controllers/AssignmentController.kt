package dev.leonzimmermann.bachelorprojekt.external.controllers

import dev.leonzimmermann.bachelorprojekt.assignment.AssignmentService
import dev.leonzimmermann.bachelorprojekt.assignment.GenerationOptions
import org.hibernate.QueryException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
@RequestMapping("/assignment")
class AssignmentController(private val assignmentService: AssignmentService) {

    private val logger = LoggerFactory.getLogger(javaClass.name)
    private val random = Random(1000)

    @PostMapping("/setupDatabase")
    fun setupDatabase(@RequestParam(defaultValue = "") fileName: String): ResponseEntity<Unit> {
        return try {
            assignmentService.setupDatabase(fileName)
            ResponseEntity(HttpStatus.OK)
        } catch (exception: Exception) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/create")
    fun createAssignment(@RequestParam(defaultValue = "") fileName: String): ResponseEntity<*> {
        if (fileName.isBlank()) {
            return ResponseEntity<String>("No filename supplied!", HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(
            assignmentService.generateNewAssignment(
                fileName,
                GenerationOptions(random, IntRange(1, 5))
            ), HttpStatus.OK
        )
    }

    @PostMapping("/validate/{objectId}")
    fun validateAssignment(
        @RequestParam("objectId") objectId: Long,
        @RequestBody solution: String
    ): ResponseEntity<*> {
        return try {
            val listOfDiscrepancies = assignmentService.validateSolution(objectId, solution)
            if (listOfDiscrepancies.isEmpty()) {
                ResponseEntity("Correct!", HttpStatus.OK)
            } else {
                ResponseEntity(
                    listOfDiscrepancies.joinToString(". ", "False... [", "]"),
                    HttpStatus.OK
                )
            }
        } catch (e: QueryException) {
            logger.error(e.stackTraceToString())
            ResponseEntity("Invalid query", HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            logger.error(e.stackTraceToString())
            ResponseEntity(e.localizedMessage, HttpStatus.BAD_REQUEST)
        }
    }

}
