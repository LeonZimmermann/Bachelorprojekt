package dev.leonzimmermann.bachelorprojekt.external.controllers

import dev.leonzimmermann.bachelorprojekt.generation.DatabaseOptions
import dev.leonzimmermann.bachelorprojekt.generation.GenerationService
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/generation")
class GenerationController(private val generationService: GenerationService) {

  @PostMapping("/generateAndPersist")
  fun generateAndPersist(@RequestParam fileName: String, @RequestParam ontologyUri: String, @RequestParam numberOfTables: Int, @RequestParam numberOfRows: Int) = runBlocking {
    try {
      generationService.persistGenerationData(
        fileName,
        generationService.generate(ontologyUri, DatabaseOptions(numberOfTables, numberOfRows))
      )
      ResponseEntity<Unit>(HttpStatus.OK)
    } catch (e: Exception) {
      e.printStackTrace()
      ResponseEntity(e.localizedMessage, HttpStatus.BAD_REQUEST)
    }
  }
}
