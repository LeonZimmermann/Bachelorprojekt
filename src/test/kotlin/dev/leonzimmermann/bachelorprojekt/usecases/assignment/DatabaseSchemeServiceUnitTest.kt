package dev.leonzimmermann.bachelorprojekt.usecases.assignment

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.*
import dev.leonzimmermann.bachelorprojekt.services.getPersonTableScheme
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class DatabaseSchemeServiceUnitTest {

  private val logger = LoggerFactory.getLogger(javaClass)

  @Autowired
  private lateinit var databaseSchemeService: DatabaseSchemeService

  private lateinit var ontModel: OntModel

  @Before
  fun loadCustomOntology() {
    val loadedBaseModel: Model = RDFDataMgr.loadModel("customontology.ttl")
    val ontModelSpec = OntModelSpec(OntModelSpec.OWL_MEM)
    ontModel = ModelFactory.createOntologyModel(ontModelSpec, loadedBaseModel)
  }

  @Test
  fun testLoadingTurtleFile() {
    // When
    val ontClasses = ontModel.listClasses().toList()
    logger.debug("OntClasses: ${ontClasses.joinToString(", ")}")
    val datatypeProperties = ontModel.listDatatypeProperties().toList()
    logger.debug("DatatypeProperties: ${datatypeProperties.joinToString(", ")}")
    val objectProperties = ontModel.listObjectProperties().toList()
    logger.debug("ObjectProperties: ${objectProperties.joinToString(", ")}")
    // Then
    assertThat(ontClasses).hasSize(5)
    assertThat(datatypeProperties).hasSize(11)
    assertThat(objectProperties).hasSize(4)
  }

  @Test
  fun testCreateDatabase() {
    // Given
    val expectedDatabaseScheme = DatabaseScheme(
      arrayOf(
        TableScheme(
          name = "Address", PropertyScheme(TABLE_PRIMARY_KEY_IDENTIFIER, ObjectIdGenerator()),
          arrayOf(),
          arrayOf(
            PropertyScheme(name = "postalCode", IntValueGenerator(IntRange(10000, 99999))),
            PropertyScheme(name = "street", valueGenerator = ValueGeneratorFromStringList("")),
            PropertyScheme(name = "streetNumber", IntValueGenerator(IntRange(1, 10000))),
            PropertyScheme(name = "country", ValueGeneratorFromStringList("")),
            PropertyScheme(name = "city", ValueGeneratorFromStringList("")),
            PropertyScheme(name = "state", ValueGeneratorFromStringList(""))
          )
        ), TableScheme(
          name = "Institution", PropertyScheme(TABLE_PRIMARY_KEY_IDENTIFIER, ObjectIdGenerator()),
          arrayOf(
            ForeignKeyScheme("address", "Address", TABLE_PRIMARY_KEY_IDENTIFIER)
          ),
          arrayOf(
            PropertyScheme(name = "name", ValueGeneratorFromStringList("")),
            PropertyScheme(name = "typeOfInstitution", ValueGeneratorFromStringList("")),
          )
        ), TableScheme(
          name = "Occupation", PropertyScheme(TABLE_PRIMARY_KEY_IDENTIFIER, ObjectIdGenerator()),
          arrayOf(
            ForeignKeyScheme("person", "Person", TABLE_PRIMARY_KEY_IDENTIFIER),
            ForeignKeyScheme("institution", "Institution", TABLE_PRIMARY_KEY_IDENTIFIER)
          ),
          arrayOf(
            PropertyScheme(name = "name", ValueGeneratorFromStringList("")),
          )
        ), TableScheme(
          name = "Relationship", PropertyScheme(TABLE_PRIMARY_KEY_IDENTIFIER, ObjectIdGenerator()),
          arrayOf(
            ForeignKeyScheme("person", "Person", TABLE_PRIMARY_KEY_IDENTIFIER),
            ForeignKeyScheme("otherPerson", "Person", TABLE_PRIMARY_KEY_IDENTIFIER)
          ),
          arrayOf(
            PropertyScheme(name = "typeOfRelationship", ValueGeneratorFromStringList("")),
          )
        ), getPersonTableScheme(TABLE_PRIMARY_KEY_IDENTIFIER)
      )
    )
    // When
    val databaseScheme = databaseSchemeService.createDatabaseSchemeFromOntology(ontModel)
    // Then
    assertThat(databaseScheme).isEqualTo(expectedDatabaseScheme)
  }

  companion object {
    private const val TABLE_PRIMARY_KEY_IDENTIFIER = "objectId"
  }
}
