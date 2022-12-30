package dev.leonzimmermann.bachelorprojekt.assignment

import dev.leonzimmermann.bachelorprojekt.generation.OntologyService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
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
    val ontology = ontologyService.createEROntology("C:\\Users\\leonz\\Downloads\\wine.rdf")
    assertEquals("Wine", ontology.getOntClass("Wine").getLabel("EN"))
    assertEquals("Region", ontology.getOntClass("Region").getLabel("EN"))
    assertThat(ontology.getOntClass("Wine").listDeclaredProperties().toList())
      .anyMatch { it.getLabel("EN") == "hasFlavor" }
    assertThat(ontology.getOntClass("Region").listInstances().toList())
      .anyMatch { it.getLabel("EN") == "TexasRegion" }
  }

}