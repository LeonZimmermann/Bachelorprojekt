package dev.leonzimmermann.bachelorprojekt.assignment

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.*
import dev.leonzimmermann.bachelorprojekt.assignment.GenerationOptions
import dev.leonzimmermann.bachelorprojekt.assignment.SQLService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.random.Random

@SpringBootTest
@RunWith(SpringRunner::class)
class SQLServiceUnitTest {

  private val logger = LoggerFactory.getLogger(javaClass)

  @Autowired
  private lateinit var sqlService: SQLService

  @Test
  fun generatedSelectStatementHasBetweenOneAndFiveParameters() {
    // Given
    val databaseScheme = DatabaseScheme(
      arrayOf(
        TableScheme(
          "Address", PropertyScheme("objectId"), emptyArray(), arrayOf(
            PropertyScheme("street"),
            PropertyScheme("streetNumber"),
            PropertyScheme("postalcode"),
            PropertyScheme("city"),
            PropertyScheme("state"),
            PropertyScheme("country"),
          )
        )
      )
    )
    val generationOptions = GenerationOptions(
      random = Random(1000),
      possibleNumberOfParameters = IntRange(1, 5),
      enableWhereClause = false
    )
    repeat(100) {
      // When
      val result = sqlService.generateSQLExpression(databaseScheme, generationOptions)
      // Then
      logger.debug("generatedSelectStatementHasBetweenOneAndFiveParameters: SQLString=${result.toSQLString().trim()}")
      assertThat(result.toSQLString().trim()).contains("SELECT")
      assertThat(
        result.toSQLString().trim()
          .matches("SELECT ([a-zA-Z]*, ){0,4}[a-zA-Z]+ FROM.*".toRegex())
      ).overridingErrorMessage("Select needs to have between one and five properties. SQL: ${result.toSQLString().trim()}").isTrue
    }
  }

  @Test
  fun generatedSelectStatementHasJoinStatementsWhenMultipleTablesAreUsed() {
    // Given
    val addressTableScheme = TableScheme(
      "Address", PropertyScheme("objectId"), emptyArray(), arrayOf(
        PropertyScheme("street"),
        PropertyScheme("streetNumber"),
      )
    )
    val personTableScheme = TableScheme(
      "Person", PropertyScheme("objectId"), arrayOf(
        ForeignKeyScheme("address", "Address", "objectId")
      ), arrayOf(
        PropertyScheme("firstname"),
        PropertyScheme("lastname")
      )
    )
    val databaseScheme = DatabaseScheme(arrayOf(addressTableScheme, personTableScheme))
    val generationOptions = GenerationOptions(
      random = Random(1000),
      possibleNumberOfParameters = IntRange(3, 3),
      startingPoint = personTableScheme,
      enableWhereClause = false
    )
    repeat(100) {
      // When
      val result = sqlService.generateSQLExpression(databaseScheme, generationOptions)
      // Then
      logger.debug("generatedSelectStatementHasJoinStatementsWhenMultipleTablesAreUsed: SQLString=${result.toSQLString().trim()}")
      assertThat(result.toSQLString().trim()).contains("SELECT")
      assertThat("SELECT ([a-zA-Z]*, ){2}[a-zA-Z]+ FROM".toRegex().containsMatchIn(result.toSQLString().trim()))
        .overridingErrorMessage("Select needs to have exactly three properties. SQL: ${result.toSQLString().trim()}")
        .isTrue
      assertThat(result.toSQLString().trim())
        .overridingErrorMessage("The SQL Statement needs to contain a JOIN expression. SQL: ${result.toSQLString().trim()}")
        .contains("JOIN Address ON Address.objectId=Person.address")
    }
  }

  @Test
  fun generatedSelectStatementHasWhereClause() {
    // Given
    val addressTableScheme = TableScheme(
      "Address", PropertyScheme("objectId", ObjectIdGenerator()), emptyArray(), arrayOf(
        PropertyScheme("street", ValueGeneratorFromStringList("Steeler Str.", "Altenessener Str.")),
        PropertyScheme("streetNumber", IntValueGenerator(IntRange(1, 10))),
        PropertyScheme("city", ValueGeneratorFromStringList("Essen", "Duesseldorf")),
        PropertyScheme("state", ValueGeneratorFromStringList("Nordrhein-Westfalen", "Berlin", "Brandenburg")),
        PropertyScheme("country", ValueGeneratorFromStringList("Deutschland", "Oesterreich", "Schweiz"))
      )
    )
    val databaseScheme = DatabaseScheme(arrayOf(addressTableScheme))
    val generationOptions = GenerationOptions(
      random = Random(1000),
      possibleNumberOfParameters = IntRange(3, 3)
    )
    repeat(100) {
      // When
      val result = sqlService.generateSQLExpression(databaseScheme, generationOptions)
      // Then
      logger.debug("generatedSelectStatementHasWhereClause: SQLString=${result.toSQLString().trim()}")
      assertThat(result.toSQLString().trim()).contains("SELECT")
      assertThat("SELECT ([a-zA-Z]*, ){2}[a-zA-Z]+ FROM".toRegex().containsMatchIn(result.toSQLString().trim()))
        .overridingErrorMessage("Select needs to have exactly three properties. SQL: ${result.toSQLString().trim()}")
        .isTrue
      // TODO Get params through regex group and check whether at least one is used in WHERE clause
      assertThat(result.toSQLString().trim())
        .overridingErrorMessage("The SQL Statement needs to contain a WHERE clause. SQL: ${result.toSQLString().trim()}")
        .contains("WHERE")
    }
  }

  // TODO Add check to test that there are no SQL Expressions, with an EqualsExpression in the where-clause, whose property is part of the select
}