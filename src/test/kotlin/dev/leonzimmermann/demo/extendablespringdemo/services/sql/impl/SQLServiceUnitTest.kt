package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.TableScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.SQLService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class SQLServiceUnitTest {

  @Autowired
  private lateinit var sqlService: SQLService

  @Test
  fun test() {
    // Given
    val databaseScheme = DatabaseScheme(arrayOf(
      TableScheme("Address", PropertyScheme("objectId", "integer"), emptyArray(), arrayOf(
        PropertyScheme("street", "string")
      ))
    ))
    // When
    val result = sqlService.generateSQLExpression(databaseScheme)
    // Then
    assertThat(result.toSQLString()).contains("SELECT")
  }

}