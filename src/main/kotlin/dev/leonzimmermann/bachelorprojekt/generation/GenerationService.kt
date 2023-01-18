package dev.leonzimmermann.bachelorprojekt.generation

interface GenerationService {
  fun generate(ontologyUri: String, datbaseOptions: DatabaseOptions): GenerationData
  suspend fun persistGenerationData(fileName: String, generationData: GenerationData)
}
