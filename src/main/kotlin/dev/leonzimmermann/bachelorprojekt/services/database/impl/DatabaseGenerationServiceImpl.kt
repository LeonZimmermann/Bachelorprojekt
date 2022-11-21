package dev.leonzimmermann.bachelorprojekt.services.database.impl

import dev.leonzimmermann.bachelorprojekt.services.database.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import org.springframework.stereotype.Service

@Service
class DatabaseGenerationServiceImpl: DatabaseGenerationService {
    override fun generateDatabaseForScheme(databaseScheme: DatabaseScheme) {
        TODO("Not yet implemented")
    }
}
