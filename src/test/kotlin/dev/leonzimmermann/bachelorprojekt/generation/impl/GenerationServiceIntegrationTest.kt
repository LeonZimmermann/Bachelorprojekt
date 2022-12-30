package dev.leonzimmermann.bachelorprojekt.generation.impl

import dev.leonzimmermann.bachelorprojekt.generation.DatabaseSchemeService
import dev.leonzimmermann.bachelorprojekt.generation.OntologyService
import dev.leonzimmermann.bachelorprojekt.assignment.QueryService
import dev.leonzimmermann.bachelorprojekt.generation.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.generation.OntologyReductionService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.mockingDetails
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
@RunWith(SpringRunner::class)
class GenerationServiceIntegrationTest {

  @Autowired
  private lateinit var ontologyService: OntologyService

  @Autowired
  private lateinit var ontologyReductionService: OntologyReductionService

  @Autowired
  private lateinit var databaseSchemeService: DatabaseSchemeService

  @Autowired
  private lateinit var databaseGenerationService: DatabaseGenerationService

  @Mock
  private lateinit var queryService: QueryService

  @Test
  fun testGenerateForCustomOntologyTtl() {
    val generationService = GenerationServiceImpl(
      ontologyService,
      ontologyReductionService,
      databaseSchemeService,
      databaseGenerationService,
      queryService
    )

    generationService.generate("customontology.ttl")

    verify(queryService).executeQuery(
      """CREATE TABLE Address(
objectId INT NOT NULL,
country VARCHAR NOT NULL,
state VARCHAR NOT NULL,
postalCode INT NOT NULL,
street VARCHAR NOT NULL,
city VARCHAR NOT NULL,
streetNumber INT NOT NULL,
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery(
      """CREATE TABLE Institution(
objectId INT NOT NULL,
typeOfInstitution VARCHAR NOT NULL,
name VARCHAR NOT NULL,
address INT NOT NULL,
FOREIGN KEY(address) REFERENCES Address(objectId),
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery(
      """CREATE TABLE Occupation(
objectId INT NOT NULL,
name VARCHAR NOT NULL,
person INT NOT NULL,
institution INT NOT NULL,
FOREIGN KEY(person) REFERENCES Person(objectId),
FOREIGN KEY(institution) REFERENCES Institution(objectId),
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery(
      """CREATE TABLE Relationship(
objectId INT NOT NULL,
typeOfRelationship VARCHAR NOT NULL,
person INT NOT NULL,
otherPerson INT NOT NULL,
FOREIGN KEY(person) REFERENCES Person(objectId),
FOREIGN KEY(otherPerson) REFERENCES Person(objectId),
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery(
      """CREATE TABLE Person(
objectId INT NOT NULL,
firstname VARCHAR NOT NULL,
lastname VARCHAR NOT NULL,
address INT NOT NULL,
FOREIGN KEY(address) REFERENCES Address(objectId),
PRIMARY KEY(objectId));
""")
  }

  @Test
  fun testGenerateForWineOntology() {
    val generationService = GenerationServiceImpl(
      ontologyService,
      ontologyReductionService,
      databaseSchemeService,
      databaseGenerationService,
      queryService
    )

    generationService.generate("C:\\Users\\leonz\\Downloads\\wine.rdf")

    verify(queryService).executeQuery("""CREATE TABLE Vintage(
objectId INT NOT NULL,
hasVintageYear INT NOT NULL,
FOREIGN KEY(hasVintageYear) REFERENCES VintageYear(objectId),
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery("""CREATE TABLE ConsumableThing(
objectId INT NOT NULL,
madeFromFruit INT NOT NULL,
FOREIGN KEY(madeFromFruit) REFERENCES Fruit(objectId),
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery("""CREATE TABLE wine(
objectId INT NOT NULL,
madeFromGrape INT NOT NULL,
hasWineDescriptor INT NOT NULL,
hasColor INT NOT NULL,
FOREIGN KEY(madeFromGrape) REFERENCES WineGrape(objectId),
FOREIGN KEY(hasWineDescriptor) REFERENCES WineDescriptor(objectId),
FOREIGN KEY(hasColor) REFERENCES WineColor(objectId),
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery("""CREATE TABLE Region(
objectId INT NOT NULL,
adjacentRegion INT NOT NULL,
FOREIGN KEY(adjacentRegion) REFERENCES Region(objectId),
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery("""CREATE TABLE Thing(
objectId INT NOT NULL,
locatedIn INT NOT NULL,
FOREIGN KEY(locatedIn) REFERENCES Region(objectId),
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery("""CREATE TABLE MealCourse(
objectId INT NOT NULL,
hasFood INT NOT NULL,
hasDrink INT NOT NULL,
FOREIGN KEY(hasFood) REFERENCES EdibleThing(objectId),
FOREIGN KEY(hasDrink) REFERENCES PotableLiquid(objectId),
PRIMARY KEY(objectId));
""")
    verify(queryService).executeQuery("""CREATE TABLE Meal(
objectId INT NOT NULL,
course INT NOT NULL,
FOREIGN KEY(course) REFERENCES MealCourse(objectId),
PRIMARY KEY(objectId));
""")
  }

  @Test
  fun printResultOfGenerateToFile() {
    val generationService = GenerationServiceImpl(
      ontologyService,
      ontologyReductionService,
      databaseSchemeService,
      databaseGenerationService,
      queryService
    )

    generationService.generate("C:\\Users\\leonz\\Downloads\\cbo.owl")

    val timestamp = DateTimeFormatter.ofPattern("yyyyMMddmmssSSSS").format(LocalDateTime.now())
    val file = File("results\\${timestamp}_cbo.sql")
    if (!file.exists()) {
      file.createNewFile()
    }
    file.writeText(mockingDetails(queryService).invocations
      .map { it.toString() }
      .joinToString("\n\n") { it.subSequence(32, it.length - 5) })
  }
}