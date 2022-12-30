package dev.leonzimmermann.bachelorprojekt.generation.impl

// TODO No dependencies to usescases.assignment! Put interfaces in corresponding service folders and use bridge pattern
import dev.leonzimmermann.bachelorprojekt.generation.DatabaseSchemeService
import dev.leonzimmermann.bachelorprojekt.generation.OntologyService
import dev.leonzimmermann.bachelorprojekt.assignment.QueryService
import dev.leonzimmermann.bachelorprojekt.generation.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.generation.GenerationService
import dev.leonzimmermann.bachelorprojekt.generation.OntologyReductionService
import dev.leonzimmermann.bachelorprojekt.services.ontology.OntologyReductionOptions
import org.springframework.stereotype.Service

@Service
internal class GenerationServiceImpl constructor(
  private val ontologyService: OntologyService,
  private val ontologyReductionService: OntologyReductionService,
  private val databaseSchemeService: DatabaseSchemeService,
  private val databaseGenerationService: DatabaseGenerationService,
  private val queryService: QueryService
) : GenerationService {
  override fun generate(ontologyUri: String, ontologyReductionOptions: OntologyReductionOptions) {
    ontologyService.createEROntology(ontologyUri)
      .let { ontologyReductionService.reduceOntology(it, ontologyReductionOptions) }
      .let { databaseSchemeService.createDatabaseSchemeFromOntology(it) }
      .let { databaseGenerationService.getDatabaseGenerationQueriesForScheme(it) }
      .forEach { queryService.executeQuery(it) }
  }
}