package dev.leonzimmermann.bachelorprojekt.generation

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import org.apache.jena.ontology.OntModel

interface DatabaseSchemeService {
  fun createDatabaseSchemeFromOntology(model: OntModel): DatabaseScheme
}