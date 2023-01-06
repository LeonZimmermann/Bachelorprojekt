package dev.leonzimmermann.bachelorprojekt.assignment

interface AssignmentService {
  fun setupDatabase(fileName: String)
  fun generateNewAssignment(fileName: String, generationOptions: GenerationOptions): Assignment
  fun validateSolution(objectId: Long, answer: String): List<String>
}
