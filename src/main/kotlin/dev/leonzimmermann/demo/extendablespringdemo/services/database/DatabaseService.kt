package dev.leonzimmermann.demo.extendablespringdemo.services.database

import org.apache.jena.ontology.OntModel

interface DatabaseService {
  fun createDatabaseFromOntology(model: OntModel)
}