package dev.leonzimmermann.bachelorprojekt.assignment

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import org.apache.jena.ontology.OntModel

interface GenerationDataReader {
    suspend fun loadOntologyFromDisk(fileName: String): OntModel
    suspend fun loadDatabaseSchemeFromDisk(fileName: String): DatabaseScheme
    suspend fun loadSQLFromDisk(fileName: String): Array<String>
}
