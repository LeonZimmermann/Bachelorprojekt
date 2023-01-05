package dev.leonzimmermann.bachelorprojekt.generation

interface GenerationService {
  fun generate(ontologyUri: String, ontologyReductionOptions: OntologyReductionOptions)
}
