package dev.leonzimmermann.demo.extendablespringdemo.services.assignment

import dev.leonzimmermann.demo.extendablespringdemo.services.sql.GenerationOptions

interface AssignmentService {
  fun generateNewAssignment(generationOptions: GenerationOptions): Assignment
  fun validateSolution(objectId: Long, answer: String): List<String>
}