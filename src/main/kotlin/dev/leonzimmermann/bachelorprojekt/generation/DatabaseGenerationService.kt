package dev.leonzimmermann.bachelorprojekt.generation

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
interface DatabaseGenerationService {
    fun getDatabaseGenerationQueriesForScheme(databaseScheme: DatabaseScheme, databaseOptions: DatabaseOptions): Array<String>
}
