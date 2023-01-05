package dev.leonzimmermann.bachelorprojekt.generation.impl

import dev.leonzimmermann.bachelorprojekt.generation.DatabaseSchemeService
import dev.leonzimmermann.bachelorprojekt.generation.OntologyService
import dev.leonzimmermann.bachelorprojekt.assignment.QueryService
import dev.leonzimmermann.bachelorprojekt.generation.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.generation.OntologyReductionService
import dev.leonzimmermann.bachelorprojekt.generation.OntologyReductionOptions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.mockingDetails
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

  @Autowired
  private lateinit var ontologyService: OntologyService

  @Autowired
  private lateinit var ontologyReductionService: OntologyReductionService

  @Autowired
  private lateinit var databaseSchemeService: DatabaseSchemeService

  @Autowired
  private lateinit var databaseGenerationService: DatabaseGenerationService

  @Mock
  private lateinit var queryService: QueryService

  @Test
  fun testGenerateForCustomOntologyTtl() {
    val generationService = GenerationServiceImpl(
      ontologyService,
      ontologyReductionService,
      databaseSchemeService,
      databaseGenerationService,
    )
    val ontologyUri = "customontology.ttl"

    generationService.generate(ontologyUri, OntologyReductionOptions(3))

    logToFile("customontology")
  }

  private fun logToFile(name: String) {
    val timestamp = DateTimeFormatter.ofPattern("yyyyMMddmmssSSSS").format(LocalDateTime.now())
    val file = File("results\\${timestamp}_$name.sql")
    if (!Files.exists(Path.of("results"))) {
      Files.createDirectory(Path.of("results"))
    }
    if (!file.exists()) {
      file.createNewFile()
    }
    file.writeText(invocationsToSQLText())
  }

  private fun invocationsToSQLText() = mockingDetails(queryService).invocations
    .map { it.toString() }
    .joinToString("\n\n") { it.subSequence(32, it.length - 5) }
}
