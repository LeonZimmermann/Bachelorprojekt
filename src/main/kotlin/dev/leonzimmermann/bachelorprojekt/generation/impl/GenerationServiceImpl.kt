package dev.leonzimmermann.bachelorprojekt.generation.impl

import dev.leonzimmermann.bachelorprojekt.generation.*
import org.springframework.stereotype.Service

@Service
internal class GenerationServiceImpl constructor(
    private val ontologyService: OntologyService,
    private val ontologyReductionService: OntologyReductionService,
    private val databaseSchemeService: DatabaseSchemeService,
    private val databaseGenerationService: DatabaseGenerationService
) : GenerationService {
    override fun generate(ontologyUri: String, ontologyReductionOptions: OntologyReductionOptions) {
        ontologyService.createEROntology(ontologyUri)
            .let { ontologyReductionService.reduceOntology(it, ontologyReductionOptions) }
            .let { databaseSchemeService.createDatabaseSchemeFromOntology(it) }
            .let { databaseGenerationService.getDatabaseGenerationQueriesForScheme(it) }
    }
}
