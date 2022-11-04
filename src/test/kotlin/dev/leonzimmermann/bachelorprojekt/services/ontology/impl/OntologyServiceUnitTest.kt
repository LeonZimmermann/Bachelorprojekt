package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.services.ontology.OntologyService
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class OntologyServiceUnitTest {

  @Autowired
  private lateinit var ontologyService: OntologyService

  @Test
  fun testCanReadOntologyFromTurtleFile() {
    val nameSpace = "http://visualdataweb.org/newOntology/"
    val ontology = ontologyService.createOntology()
    assertEquals("Person", ontology.getOntClass("${nameSpace}Person").getLabel("EN"))
  }

}