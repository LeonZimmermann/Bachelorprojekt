package dev.leonzimmermann.bachelorprojekt.external.controllers

import dev.leonzimmermann.bachelorprojekt.services.persistence.PersistenceService
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/persistence")
class PersistenceController(private val persistenceService: PersistenceService) {

  @GetMapping("/listFiles")
  fun listFiles() = runBlocking {
    ResponseEntity(persistenceService.listFiles(), HttpStatus.OK)
  }

  @DeleteMapping("/remove")
  fun removeFile(@RequestParam(defaultValue = "") fileName: String) = runBlocking {
    if (fileName.isBlank()) {
      ResponseEntity<String>("No filename supplied!", HttpStatus.BAD_REQUEST)
    }
    ResponseEntity(persistenceService.removeFile(fileName), HttpStatus.OK)
  }
}