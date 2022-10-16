package dev.leonzimmermann.demo.extendablespringdemo.services.database

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import org.apache.jena.ontology.OntModel

interface DatabaseSchemeService {
  fun createDatabaseSchemeFromOntology(model: OntModel): DatabaseScheme
}