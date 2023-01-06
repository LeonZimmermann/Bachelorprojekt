package dev.leonzimmermann.bachelorprojekt.generation

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import org.apache.jena.ontology.OntModel

class GenerationData {
    private var ontology: OntModel? = null
    private var databaseScheme: DatabaseScheme? = null
    private var queries: Array<String>? = null

    fun setOntology(ontology: OntModel): OntModel {
        this.ontology = ontology
        return ontology
    }

    fun setDatabaseScheme(databaseScheme: DatabaseScheme): DatabaseScheme {
        this.databaseScheme = databaseScheme
        return databaseScheme
    }

    fun setQueries(queries: Array<String>): Array<String> {
        this.queries = queries
        return queries
    }

    fun getOntology(): OntModel = ontology!!
    fun getDatabaseScheme(): DatabaseScheme = databaseScheme!!
    fun getQueries(): Array<String> = queries!!
}
