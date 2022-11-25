package dev.leonzimmermann.bachelorprojekt.usecases.generation

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme

interface DatabaseGenerationService {
    fun getDatabaseGenerationQueriesForScheme(databaseScheme: DatabaseScheme): Array<String>
}
