package dev.leonzimmermann.bachelorprojekt.generation.impl

// TODO No dependencies to usescases.assignment! Put interfaces in corresponding service folders and use bridge pattern
import dev.leonzimmermann.bachelorprojekt.generation.DatabaseSchemeService
import dev.leonzimmermann.bachelorprojekt.generation.OntologyService
import dev.leonzimmermann.bachelorprojekt.assignment.QueryService
import dev.leonzimmermann.bachelorprojekt.generation.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.generation.GenerationService
import org.springframework.stereotype.Service

@Service
internal class GenerationServiceImpl constructor(
  private val ontologyService: OntologyService,
  private val databaseSchemeService: DatabaseSchemeService,
  private val databaseGenerationService: DatabaseGenerationService,
  private val queryService: QueryService
) : GenerationService {
  override fun generate(ontologyUri: String) {
    ontologyService.createEROntology(ontologyUri)
      .let { databaseSchemeService.createDatabaseSchemeFromOntology(it) }
      .let { databaseGenerationService.getDatabaseGenerationQueriesForScheme(it) }
      .forEach { queryService.executeQuery(it) }
  }
}