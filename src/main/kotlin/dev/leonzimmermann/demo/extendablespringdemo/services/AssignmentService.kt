package dev.leonzimmermann.demo.extendablespringdemo.services

import dev.leonzimmermann.demo.extendablespringdemo.models.fixedDatabase.Assignment

interface AssignmentService {
  fun generateNewAssignment(): Assignment
  fun solveAssignmentAndReturnListOfDiscrepancies(objectId: Long, answer: String): List<String>
}