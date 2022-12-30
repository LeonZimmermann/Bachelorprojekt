package dev.leonzimmermann.bachelorprojekt.services.persistence

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import org.apache.jena.ontology.OntModel

interface PersistenceService {
  fun saveOntologyToDisk(fileName: String, ontology: OntModel)
  fun saveDatabaseSchemeToDisk(fileName: String, databaseScheme: DatabaseScheme)
  fun saveSQLToDisk(fileName: String, queries: List<String>)

  fun loadOntologyFromDisk(fileName: String): OntModel
  fun loadDatabaseSchemeFromDisk(fileName: String): DatabaseScheme
  fun loadSQLFromDisk(fileName: String): List<String>

  fun listFiles(): List<String>
}