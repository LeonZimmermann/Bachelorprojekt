package dev.leonzimmermann.bachelorprojekt.usecases.generation.impl

// TODO No dependencies to usescases.assignment! Put interfaces in corresponding service folders and use bridge pattern
import dev.leonzimmermann.bachelorprojekt.usecases.assignment.DatabaseSchemeService
import dev.leonzimmermann.bachelorprojekt.usecases.assignment.OntologyService
import dev.leonzimmermann.bachelorprojekt.usecases.assignment.QueryService
import dev.leonzimmermann.bachelorprojekt.usecases.generation.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.usecases.generation.GenerationService
import org.springframework.stereotype.Service

@Service
class GenerationServiceImpl constructor(
  private val ontologyService: OntologyService,
  private val databaseSchemeService: DatabaseSchemeService,
  private val databaseGenerationService: DatabaseGenerationService,
  private val queryService: QueryService
) : GenerationService {
  override fun generate() {
    ontologyService.createEROntology()
      .let { databaseSchemeService.createDatabaseSchemeFromOntology(it) }
      .let { databaseGenerationService.getDatabaseGenerationQueriesForScheme(it) }
      .forEach { queryService.executeQuery(it) }
  }
}