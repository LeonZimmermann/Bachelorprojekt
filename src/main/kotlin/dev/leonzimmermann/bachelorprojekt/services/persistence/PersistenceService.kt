package dev.leonzimmermann.bachelorprojekt.services.persistence

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import org.apache.jena.ontology.OntModel

interface PersistenceService {
  suspend fun saveOntologyToDisk(fileName: String, ontology: OntModel): Boolean
  suspend fun saveDatabaseSchemeToDisk(fileName: String, databaseScheme: DatabaseScheme): Boolean
  suspend fun saveSQLToDisk(fileName: String, queries: Array<String>): Boolean

  suspend fun loadOntologyFromDisk(fileName: String): OntModel
  suspend fun loadDatabaseSchemeFromDisk(fileName: String): DatabaseScheme
  suspend fun loadSQLFromDisk(fileName: String): Array<String>

  suspend fun removeFile(fileName: String): Boolean

  suspend fun listFiles(): Array<String>
}
