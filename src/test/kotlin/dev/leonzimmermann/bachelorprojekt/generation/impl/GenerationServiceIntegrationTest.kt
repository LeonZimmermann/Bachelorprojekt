package dev.leonzimmermann.bachelorprojekt.generation.impl

import dev.leonzimmermann.bachelorprojekt.assignment.QueryService
import dev.leonzimmermann.bachelorprojekt.generation.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.mockingDetails
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
@RunWith(SpringRunner::class)
class GenerationServiceIntegrationTest {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var ontologyService: OntologyService

    @Autowired
    private lateinit var ontologyReductionService: OntologyReductionService

    @Autowired
    private lateinit var databaseSchemeService: DatabaseSchemeService

    @Autowired
    private lateinit var databaseGenerationService: DatabaseGenerationService

    @Mock
    private lateinit var generationDataWriter: GenerationDataWriter

    @Test
    fun testGenerateForCustomOntologyTtl() {
        val generationService = GenerationServiceImpl(
            ontologyService,
            ontologyReductionService,
            databaseSchemeService,
            databaseGenerationService,
            generationDataWriter
        )
        val ontologyUri = "customontology.ttl"

        val generationData = generationService.generate(ontologyUri, OntologyReductionOptions(3))

        logger.debug(generationData.toString())
    }
}
