package dev.leonzimmermann.demo.extendablespringdemo.services.assignment

interface AssignmentService {
  fun generateNewAssignment(): Assignment
  fun validateSolution(objectId: Long, answer: String): List<String>
}