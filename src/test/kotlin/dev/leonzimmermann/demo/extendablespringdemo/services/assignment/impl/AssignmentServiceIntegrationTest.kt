package dev.leonzimmermann.demo.extendablespringdemo.services.assignment.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.AssignmentService
import dev.leonzimmermann.demo.extendablespringdemo.services.database.impl.DatabaseSchemeServiceImpl
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.*
import dev.leonzimmermann.demo.extendablespringdemo.services.ontology.impl.OntologyServiceImpl
import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryService
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.GenerationOptions
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.SQLService
import junit.framework.TestCase.assertNotNull
import org.apache.jena.rdf.model.ModelFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import kotlin.random.Random

@SpringBootTest
@RunWith(SpringRunner::class)
class AssignmentServiceIntegrationTest {

  @MockBean
  private lateinit var ontologyService: OntologyServiceImpl

  @MockBean
  private lateinit var databaseSchemeService: DatabaseSchemeServiceImpl

  @Autowired
  private lateinit var sqlService: SQLService

  @Autowired
  private lateinit var queryService: QueryService

  @Test
  fun `when generateNewAssignment() is called, then the method returns a valid assignment`() {
    // Given
    val assignmentService = AssignmentServiceImpl(
      ontologyService,
      databaseSchemeService,
      sqlService,
      queryService
    )
    val generationOptions = GenerationOptions(Random(1000), IntRange(1, 5))
    val addressTableScheme = TableScheme(
      "Address", PropertyScheme("objectId", ObjectIdGenerator()), emptyArray(), arrayOf(
        PropertyScheme("street", ValueGeneratorFromStringList("Steeler Str.", "Altenessener Str.")),
        PropertyScheme("streetNumber", IntValueGenerator(IntRange(1, 10))),
        PropertyScheme("city", ValueGeneratorFromStringList("Essen", "Duesseldorf")),
        PropertyScheme(
          "state",
          ValueGeneratorFromStringList("Nordrhein-Westfalen", "Berlin", "Brandenburg")
        ),
        PropertyScheme(
          "country",
          ValueGeneratorFromStringList("Deutschland", "Oesterreich", "Schweiz")
        )
      )
    )
    val databaseScheme = DatabaseScheme(arrayOf(addressTableScheme))
    given(ontologyService.createOntology()).willReturn(ModelFactory.createOntologyModel())
    given(databaseSchemeService.createDatabaseSchemeFromOntology(any())).willReturn(databaseScheme)
    // When
    val assignment = assignmentService.generateNewAssignment(generationOptions)
    // Then
    // TODO If the where-clause contains an equals expression, the property should not be part of the select
    assertThat(assignment.stem).isEqualTo("Query all the state where the state equals to Brandenburg.")
    assertThat(assignment.sqlExpression.toSQLString()).isEqualTo("SELECT state FROM Address\n" +
        "WHERE state='Brandenburg'")
  }
}