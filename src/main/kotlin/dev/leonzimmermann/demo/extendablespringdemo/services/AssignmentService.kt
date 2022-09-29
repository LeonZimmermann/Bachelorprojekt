package dev.leonzimmermann.demo.extendablespringdemo.services

import dev.leonzimmermann.demo.extendablespringdemo.models.Assignment

interface AssignmentService {
  fun generateNewAssignment(): Assignment
  fun solveAssignmentAndReturnListOfDiscrepancies(objectId: Int, answer: String): List<String>
}