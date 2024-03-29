package dev.leonzimmermann.bachelorprojekt.generation

import org.apache.jena.ontology.OntModel

interface OntologyReductionService {
  fun reduceOntology(ontModel: OntModel, databaseOptions: DatabaseOptions): OntModel
}
