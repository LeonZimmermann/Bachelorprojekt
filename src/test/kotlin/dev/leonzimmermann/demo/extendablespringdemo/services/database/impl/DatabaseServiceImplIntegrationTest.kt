package dev.leonzimmermann.demo.extendablespringdemo.services.database.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.DatabaseService
import dev.leonzimmermann.demo.extendablespringdemo.services.ontology.OntologyService
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class DatabaseServiceImplIntegrationTest {

  @Autowired
  private lateinit var ontologyService: OntologyService
  @Autowired
  private lateinit var databaseService: DatabaseService

  @Test
  fun testCreateDatabase() {
    val ontologyModel = ontologyService.createOntology()
    databaseService.createDatabaseFromOntology(ontologyModel)
  }
}