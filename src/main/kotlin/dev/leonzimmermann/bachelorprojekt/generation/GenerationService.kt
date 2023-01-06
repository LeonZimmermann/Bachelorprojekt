package dev.leonzimmermann.bachelorprojekt.generation

interface GenerationService {
  fun generate(ontologyUri: String, ontologyReductionOptions: OntologyReductionOptions): GenerationData
  suspend fun persistGenerationData(fileName: String, generationData: GenerationData)
}
