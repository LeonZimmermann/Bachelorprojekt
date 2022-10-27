package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.TableScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.GenerationOptions
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.SQLService
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.*
import dev.leonzimmermann.demo.extendablespringdemo.util.minus
import org.springframework.stereotype.Service
import kotlin.random.Random
import kotlin.random.nextInt

@Service
class SQLServiceImpl : SQLService {

  override fun generateSQLExpression(
    databaseScheme: DatabaseScheme,
    generationOptions: GenerationOptions
  ): SQLExpression {
    require(databaseScheme.tables.isNotEmpty())

    val startingTable = selectRandomTable(generationOptions.random, databaseScheme)
    val fromStatement = FromStatement(SQLTable(startingTable.name))
    // TODO Different logic for multiple tables
    return SelectStatement(
      selectProperties = selectMultipleRandomProperties(
        generationOptions.random,
        startingTable,
        generationOptions.random.nextInt(generationOptions.possibleNumberOfParameters)
      ),
      fromStatement = fromStatement
    )
  }

  private fun selectMultipleRandomProperties(
    random: Random,
    startingTable: TableScheme,
    numberOfProperties: Int
  ): SQLEnumeration<SQLProperty> {
    val selectProperties = mutableListOf<PropertyScheme>()
    repeat(numberOfProperties) {
      selectProperties += selectRandomProperty(
        random,
        startingTable,
        exclude = selectProperties.toTypedArray()
      )
    }
    return SQLEnumeration(*selectProperties.map { SQLProperty(it.name) }.toTypedArray())
  }

  private fun selectRandomTable(random: Random, databaseScheme: DatabaseScheme): TableScheme =
    databaseScheme.tables[random.nextInt(databaseScheme.tables.size)]

  private fun selectRandomProperty(
    random: Random,
    tableScheme: TableScheme,
    exclude: Array<PropertyScheme> = emptyArray()
  ): PropertyScheme {
    val arrayOfAvailableProperties = tableScheme.properties - exclude
    return arrayOfAvailableProperties[random.nextInt(arrayOfAvailableProperties.size)]
  }
}