package dev.leonzimmermann.bachelorprojekt.assignment.impl

import dev.leonzimmermann.bachelorprojekt.assignment.GenerationDataReader
import dev.leonzimmermann.bachelorprojekt.services.database.impl.DatabaseSchemeServiceImpl
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.*
import dev.leonzimmermann.bachelorprojekt.getAdressTableScheme
import dev.leonzimmermann.bachelorprojekt.services.ontology.impl.OntologyServiceImpl
import dev.leonzimmermann.bachelorprojekt.assignment.QueryService
import dev.leonzimmermann.bachelorprojekt.assignment.GenerationOptions
import dev.leonzimmermann.bachelorprojekt.assignment.SQLService
import kotlinx.coroutines.runBlocking
import org.apache.jena.rdf.model.ModelFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
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

  @Autowired
  private lateinit var sqlService: SQLService

  @Autowired
  private lateinit var queryService: QueryService

  @Mock
  private lateinit var generationDataReader: GenerationDataReader

  @Test
  fun `when generateNewAssignment() is called, then the method returns a valid assignment`() = runBlocking {
    // Given
    val assignmentService = AssignmentServiceImpl(
      generationDataReader,
      sqlService,
      queryService
    )
    val generationOptions = GenerationOptions(Random(1000), IntRange(1, 5))
    val databaseScheme = DatabaseScheme(arrayOf(getAdressTableScheme()))
    given(generationDataReader.loadDatabaseSchemeFromDisk("generateNewAssignmentTest"))
      .willReturn(databaseScheme)
    // When
    val assignment = assignmentService.generateNewAssignment("generateNewAssignmentTest", generationOptions)
    // Then
    // TODO If the where-clause contains an equals expression, the property should not be part of the select
    assertThat(assignment.stem).isEqualTo("Query all the state where the state equals to Brandenburg.")
    assertThat(assignment.sqlExpression.toSQLString()).isEqualTo("SELECT state FROM Address\n" +
        "WHERE state='Brandenburg'")
  }
}
