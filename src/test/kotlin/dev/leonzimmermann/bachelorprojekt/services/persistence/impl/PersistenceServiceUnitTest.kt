package dev.leonzimmermann.bachelorprojekt.services.persistence.impl

import dev.leonzimmermann.bachelorprojekt.getAdressTableScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import kotlinx.coroutines.runBlocking
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PersistenceServiceUnitTest {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val persistenceService = PersistenceServiceImpl()

    @Test
    fun testPersistenceForOntology() {
        val ontology = ModelFactory.createOntologyModel(
            OntModelSpec(OntModelSpec.OWL_MEM),
            RDFDataMgr.loadModel("customontology.ttl")
        )
        val fileName = "testPersistenceForOntologyScheme"
        runBlocking {
            assertThat(persistenceService.saveOntologyToDisk(fileName, ontology)).isTrue
            assertThat(persistenceService.loadOntologyFromDisk(fileName).listObjects().toList())
                .isEqualTo(ontology.listObjects().toList())
        }
    }

    @Test
    fun testPersistenceForDatabaseScheme() {
        val databaseScheme = DatabaseScheme(arrayOf(getAdressTableScheme()))
        val fileName = "testPersistenceForDatabaseScheme"
        runBlocking {
            assertThat(persistenceService.saveDatabaseSchemeToDisk(fileName, databaseScheme)).isTrue
            assertThat(persistenceService.loadDatabaseSchemeFromDisk(fileName)).isEqualTo(
                databaseScheme
            )
        }
    }

    @After
    fun teardown() {
        logger.debug("PersistenceServiceUnitTest: teardown")
        runBlocking {
            persistenceService.listFiles().forEach {
                persistenceService.removeFile(it)
            }
        }
    }
}
