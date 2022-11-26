package dev.leonzimmermann.bachelorprojekt.assignment

interface AssignmentService {
  fun generateNewAssignment(generationOptions: GenerationOptions): Assignment
  fun validateSolution(objectId: Long, answer: String): List<String>
}