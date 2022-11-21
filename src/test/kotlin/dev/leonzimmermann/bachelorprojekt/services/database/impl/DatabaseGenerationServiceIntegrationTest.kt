package dev.leonzimmermann.bachelorprojekt.services.database.impl

import dev.leonzimmermann.bachelorprojekt.services.database.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.bachelorprojekt.services.getAdressTableScheme
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager

@SpringBootTest
@RunWith(SpringRunner::class)
class DatabaseGenerationServiceIntegrationTest {

    @Autowired
    private lateinit var databaseGenerationService: DatabaseGenerationService

    @Test
    fun generateDatabaseForScheme() {
        databaseGenerationService.generateDatabaseForScheme(DatabaseScheme(arrayOf(getAdressTableScheme())))


    }
}
