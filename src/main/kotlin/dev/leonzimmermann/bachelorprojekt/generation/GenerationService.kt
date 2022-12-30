package dev.leonzimmermann.bachelorprojekt.generation

import dev.leonzimmermann.bachelorprojekt.services.ontology.OntologyReductionOptions

interface GenerationService {
  fun generate(ontologyUri: String, ontologyReductionOptions: OntologyReductionOptions)
}