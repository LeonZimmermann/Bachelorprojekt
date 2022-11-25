package dev.leonzimmermann.bachelorprojekt.usecases.assignment

interface AssignmentService {
  fun generateNewAssignment(generationOptions: GenerationOptions): Assignment
  fun validateSolution(objectId: Long, answer: String): List<String>
}