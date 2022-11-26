package dev.leonzimmermann.bachelorprojekt.generation

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.bachelorprojekt.getAdressTableScheme
import dev.leonzimmermann.bachelorprojekt.getPersonTableScheme
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class DatabaseGenerationServiceUnitTest {

  private val logger: Logger = LoggerFactory.getLogger(javaClass)

  @Autowired
  private lateinit var databaseGenerationService: DatabaseGenerationService

  @Test
  fun getDatabaseGenerationQueriesForScheme() {
    val createTablePersonQuery = """
            CREATE TABLE Person(
            objectId INT NOT NULL,
            firstname VARCHAR NOT NULL,
            lastname VARCHAR NOT NULL,
            address INT NOT NULL,
            FOREIGN KEY(address) REFERENCES Address(objectId),
            PRIMARY KEY(objectId));
        """.trimIndent()
    val createTableAddressQuery = """
            CREATE TABLE Address(
            objectId INT NOT NULL,
            street VARCHAR NOT NULL,
            streetNumber INT NOT NULL,
            city VARCHAR NOT NULL,
            state VARCHAR NOT NULL,
            country VARCHAR NOT NULL,
            PRIMARY KEY(objectId));
        """.trimIndent()
    val queries = databaseGenerationService.getDatabaseGenerationQueriesForScheme(
      DatabaseScheme(
        arrayOf(
          getPersonTableScheme("objectId"),
          getAdressTableScheme()
        )
      )
    )
    logger.debug("getDatabaseGenerationQueriesForScheme: queries=${queries.joinToString("\n")}")
    assertThat(queries)
      .hasSize(2)
      .anyMatch { it.contains(createTablePersonQuery) }
      .anyMatch { it.contains(createTableAddressQuery) }
  }
}
