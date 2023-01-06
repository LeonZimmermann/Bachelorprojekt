package dev.leonzimmermann.bachelorprojekt.generation

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import org.apache.jena.ontology.OntModel

interface GenerationDataWriter {
    suspend fun saveOntologyToDisk(fileName: String, ontology: OntModel): Boolean
    suspend fun saveDatabaseSchemeToDisk(fileName: String, databaseScheme: DatabaseScheme): Boolean
    suspend fun saveSQLToDisk(fileName: String, queries: Array<String>): Boolean
    suspend fun removeFile(fileName: String): Boolean
    suspend fun listFiles(): Array<String>
}
