package dev.leonzimmermann.demo.extendablespringdemo.services.database

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import org.apache.jena.ontology.OntModel

interface DatabaseService {
  fun createDatabaseFromOntology(model: OntModel): DatabaseScheme
}