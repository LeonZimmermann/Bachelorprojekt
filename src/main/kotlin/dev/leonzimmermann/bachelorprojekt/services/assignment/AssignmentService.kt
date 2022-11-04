package dev.leonzimmermann.bachelorprojekt.services.assignment

import dev.leonzimmermann.bachelorprojekt.services.sql.GenerationOptions

interface AssignmentService {
  fun generateNewAssignment(generationOptions: GenerationOptions): Assignment
  fun validateSolution(objectId: Long, answer: String): List<String>
}