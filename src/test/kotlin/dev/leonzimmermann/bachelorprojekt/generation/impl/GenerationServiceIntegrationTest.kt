package dev.leonzimmermann.bachelorprojekt.generation.impl

import dev.leonzimmermann.bachelorprojekt.assignment.DatabaseSchemeService
import dev.leonzimmermann.bachelorprojekt.assignment.OntologyService
import dev.leonzimmermann.bachelorprojekt.assignment.QueryService
import dev.leonzimmermann.bachelorprojekt.generation.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.generation.impl.GenerationServiceImpl
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class GenerationServiceIntegrationTest {

  @Autowired
  private lateinit var ontologyService: OntologyService

  @Autowired
  private lateinit var databaseSchemeService: DatabaseSchemeService

  @Autowired
  private lateinit var databaseGenerationService: DatabaseGenerationService

  @Mock
  private lateinit var queryService: QueryService

  @Test
  fun testGenerate() {
    val generationService = GenerationServiceImpl(
      ontologyService,
      databaseSchemeService,
      databaseGenerationService,
      queryService
    )

    generationService.generate()

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
}