package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.TableScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.GenerationOptions
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.SQLService
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
          "Address", PropertyScheme("objectId", "integer"), emptyArray(), arrayOf(
            PropertyScheme("street", "string"),
            PropertyScheme("streetNumber", "integer"),
            PropertyScheme("postalcode", "integer"),
            PropertyScheme("city", "string"),
            PropertyScheme("state", "string"),
            PropertyScheme("country", "string"),
          )
        )
      )
    )
    val generationOptions = GenerationOptions(
      random = Random(1000),
      possibleNumberOfParameters = IntRange(1, 5)
    )
    repeat(100) {
      // When
      val result = sqlService.generateSQLExpression(databaseScheme, generationOptions)
      // Then
      logger.debug("generatedSelectStatementHasBetweenOneAndFiveParameters: SQLString=${result.toSQLString()}")
      assertThat(result.toSQLString()).contains("SELECT")
      assertThat(
        result.toSQLString()
          .matches("SELECT (([a-zA-Z]*), |([a-zA-Z]*)){1,5} FROM.*".toRegex(RegexOption.MULTILINE))
      ).overridingErrorMessage("Select needs to have between one and five properties. SQL: ${result.toSQLString()}").isTrue
    }
  }

}