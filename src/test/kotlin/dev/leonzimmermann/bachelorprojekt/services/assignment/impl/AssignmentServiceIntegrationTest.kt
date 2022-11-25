package dev.leonzimmermann.bachelorprojekt.services.assignment.impl

import dev.leonzimmermann.bachelorprojekt.services.database.impl.DatabaseSchemeServiceImpl
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.*
import dev.leonzimmermann.bachelorprojekt.services.getAdressTableScheme
import dev.leonzimmermann.bachelorprojekt.services.ontology.impl.OntologyServiceImpl
import dev.leonzimmermann.bachelorprojekt.usecases.assignment.QueryService
import dev.leonzimmermann.bachelorprojekt.usecases.assignment.GenerationOptions
import dev.leonzimmermann.bachelorprojekt.usecases.assignment.SQLService
import dev.leonzimmermann.bachelorprojekt.usecases.assignment.impl.AssignmentServiceImpl
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
    val databaseScheme = DatabaseScheme(arrayOf(getAdressTableScheme()))
    given(ontologyService.createEROntology()).willReturn(ModelFactory.createOntologyModel())
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
