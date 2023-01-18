package dev.leonzimmermann.bachelorprojekt.generation.impl

import dev.leonzimmermann.bachelorprojekt.generation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
internal class GenerationServiceImpl constructor(
    private val ontologyService: OntologyService,
    private val ontologyReductionService: OntologyReductionService,
    private val databaseSchemeService: DatabaseSchemeService,
    private val databaseGenerationService: DatabaseGenerationService,
    private val generationDataWriter: GenerationDataWriter
) : GenerationService {
    override fun generate(
        ontologyUri: String,
        databaseOptions: DatabaseOptions
    ): GenerationData {
        val generationData = GenerationData()
        ontologyService.createEROntology(ontologyUri)
            .let { ontologyReductionService.reduceOntology(it, databaseOptions) }
            .let { generationData.setOntology(it) }
            .let { databaseSchemeService.createDatabaseSchemeFromOntology(it) }
            .let { generationData.setDatabaseScheme(it) }
            .let { databaseGenerationService.getDatabaseGenerationQueriesForScheme(it, databaseOptions) }
            .let { generationData.setQueries(it) }
        return generationData
    }

    override suspend fun persistGenerationData(
        fileName: String,
        generationData: GenerationData
    ): Unit = withContext(Dispatchers.IO) {
        generationDataWriter.saveOntologyToDisk(fileName, generationData.getOntology())
        generationDataWriter.saveDatabaseSchemeToDisk(fileName, generationData.getDatabaseScheme())
        generationDataWriter.saveSQLToDisk(fileName, generationData.getQueries())
    }
}
