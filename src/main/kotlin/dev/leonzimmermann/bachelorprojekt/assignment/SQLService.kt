package dev.leonzimmermann.bachelorprojekt.assignment

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.bachelorprojekt.services.sql.model.SQLExpression

interface SQLService {
  fun generateSQLExpression(databaseScheme: DatabaseScheme, generationOptions: GenerationOptions): SQLExpression
}