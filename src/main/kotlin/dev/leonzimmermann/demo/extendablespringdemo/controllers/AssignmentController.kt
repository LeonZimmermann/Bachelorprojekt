package dev.leonzimmermann.demo.extendablespringdemo.controllers

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.impl.AssignmentServiceImpl
import org.hibernate.QueryException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/assignment")
class AssignmentController(
  @Autowired private val assignmentService: AssignmentServiceImpl
) {

  private val logger = LoggerFactory.getLogger(javaClass.name)

  @GetMapping("/create")
  fun createAssignment(): ResponseEntity<Any> {
    return ResponseEntity(assignmentService.generateNewAssignment(), HttpStatus.OK)
  }

  @PostMapping("/validate/{objectId}")
  fun validateAssignment(
    @RequestParam("objectId") objectId: Long,
    @RequestBody solution: String
  ): ResponseEntity<Any> {
    return try {
      val listOfDiscrepancies = assignmentService.validateSolution(objectId, solution)
      if (listOfDiscrepancies.isEmpty()) {
        ResponseEntity("Correct!", HttpStatus.OK)
      } else {
        ResponseEntity(listOfDiscrepancies.joinToString(". ", "False... [", "]"), HttpStatus.OK)
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