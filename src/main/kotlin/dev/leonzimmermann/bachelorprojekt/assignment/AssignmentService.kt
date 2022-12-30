package dev.leonzimmermann.bachelorprojekt.assignment

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme

interface AssignmentService {
  fun generateNewAssignment(databaseScheme: DatabaseScheme, generationOptions: GenerationOptions): Assignment
  fun validateSolution(objectId: Long, answer: String): List<String>
}