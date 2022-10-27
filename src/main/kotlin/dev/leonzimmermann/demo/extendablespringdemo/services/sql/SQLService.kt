package dev.leonzimmermann.demo.extendablespringdemo.services.sql

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.SQLExpression

interface SQLService {
  fun generateSQLExpression(databaseScheme: DatabaseScheme, generationOptions: GenerationOptions): SQLExpression
}