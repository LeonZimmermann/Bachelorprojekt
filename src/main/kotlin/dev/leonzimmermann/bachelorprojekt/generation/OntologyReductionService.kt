package dev.leonzimmermann.bachelorprojekt.generation

import dev.leonzimmermann.bachelorprojekt.services.ontology.OntologyReductionOptions
import org.apache.jena.ontology.OntModel

interface OntologyReductionService {
  fun reduceOntology(ontModel: OntModel, ontologyReductionOptions: OntologyReductionOptions): OntModel
}