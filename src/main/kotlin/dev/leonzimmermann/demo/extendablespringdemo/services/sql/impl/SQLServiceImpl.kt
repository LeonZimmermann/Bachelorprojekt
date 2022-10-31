package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.ForeignKeyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.TableScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.GenerationOptions
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.SQLService
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.*
import dev.leonzimmermann.demo.extendablespringdemo.util.minus
import org.springframework.stereotype.Service
import java.lang.Integer.min
import kotlin.random.Random
import kotlin.random.nextInt

@Service
class SQLServiceImpl : SQLService {

  // TODO Implement Where next. Think of the semantic for the query first, then determine the properties and tables
  override fun generateSQLExpression(
    databaseScheme: DatabaseScheme,
    generationOptions: GenerationOptions
  ): SQLExpression {
    require(databaseScheme.tables.isNotEmpty())
    val startingTable =
      generationOptions.startingPoint ?: selectRandomTable(generationOptions.random, databaseScheme)

    val propertySelectionPaths = createPropertySelectionPaths(
      generationOptions,
      databaseScheme,
      startingTable
    )

    return SelectStatement(
      selectProperties = selectRandomPropertiesFromPropertySelectionPaths(
        propertySelectionPaths,
        generationOptions
      ),
      fromStatement = FromStatement(SQLTable(startingTable.name)),
      joinExpressions = createJoinExpressions(propertySelectionPaths)
    )
  }

  // TODO This algorithm is traversing the tables in depth, but they should be traversed in breadth
  private fun createPropertySelectionPaths(
    generationOptions: GenerationOptions,
    databaseScheme: DatabaseScheme,
    startingTable: TableScheme
  ): Array<PropertySelectionPath> {
    var currentTable = startingTable
    var numberOfPropertiesToSelect =
      generationOptions.random.nextInt(generationOptions.possibleNumberOfParameters)
    val propertySelectionPaths = mutableListOf<PropertySelectionPath>()
    while (numberOfPropertiesToSelect > 0) {
      val numberOfPropertiesToSelectForTable = determineNumberOfPropertiesToSelectForTable(
        generationOptions.random,
        currentTable,
        numberOfPropertiesToSelect
      )
      numberOfPropertiesToSelect -= numberOfPropertiesToSelectForTable
      if (numberOfPropertiesToSelect > 0 && currentTable.foreignKeys.isNotEmpty()) {
        val foreignKey = selectRandomForeignKey(
          generationOptions.random,
          currentTable
        )
        databaseScheme.tables.find { it.name == foreignKey.referenceTable }?.let {
          propertySelectionPaths += PropertySelectionPath(
            currentTable,
            numberOfPropertiesToSelectForTable,
            foreignKey
          )
          currentTable = it
        }
      } else {
        propertySelectionPaths += PropertySelectionPath(
          currentTable,
          numberOfPropertiesToSelectForTable,
          null
        )
        break
      }
    }
    return propertySelectionPaths.toTypedArray()
  }

  private fun selectRandomPropertiesFromPropertySelectionPaths(
    propertySelectionPaths: Array<PropertySelectionPath>,
    generationOptions: GenerationOptions
  ): SQLEnumeration<SQLProperty> {
    val selectedProperties = propertySelectionPaths.flatMap {
      selectFixedNumberOfRandomPropertiesFromTable(
        generationOptions.random,
        it.tableScheme,
        it.numberOfPropertiesToSelect
      ).toList()
    }.map { SQLProperty(it.name) }
      .toTypedArray()
    return SQLEnumeration(*selectedProperties)
  }

  private fun createJoinExpressions(propertySelectionPaths: Array<PropertySelectionPath>): Array<JoinExpression> {
    return propertySelectionPaths
      .filter { it.foreignKey != null }
      .map { mapPropertySelectionPathToJoinExpression(it) }
      .toTypedArray()
  }

  private fun mapPropertySelectionPathToJoinExpression(propertySelectionPath: PropertySelectionPath): JoinExpression {
    requireNotNull(propertySelectionPath.foreignKey)
    return JoinExpression(
      SQLTable(propertySelectionPath.foreignKey.referenceTable),
      SQLTable(propertySelectionPath.tableScheme.name),
      SQLProperty(propertySelectionPath.foreignKey.referenceField),
      SQLProperty(propertySelectionPath.foreignKey.propertyName)

    )
  }

  data class PropertySelectionPath(
    val tableScheme: TableScheme,
    val numberOfPropertiesToSelect: Int,
    val foreignKey: ForeignKeyScheme? = null,
  )

  private fun determineNumberOfPropertiesToSelectForTable(
    random: Random,
    tableScheme: TableScheme,
    numberOfPropertiesToSelect: Int
  ): Int {
    return min(tableScheme.properties.size, numberOfPropertiesToSelect)
  }

  private fun selectFixedNumberOfRandomPropertiesFromTable(
    random: Random,
    tableScheme: TableScheme,
    numberOfPropertiesToSelect: Int
  ): Array<PropertyScheme> {
    require(tableScheme.properties.size >= numberOfPropertiesToSelect)
    val selectedProperties = mutableListOf<PropertyScheme>()
    repeat(numberOfPropertiesToSelect) {
      selectedProperties += selectRandomProperty(
        random,
        tableScheme,
        exclude = selectedProperties.toTypedArray()
      )
    }
    return selectedProperties.toTypedArray()
  }

  private fun selectRandomTable(random: Random, databaseScheme: DatabaseScheme): TableScheme {
    require(databaseScheme.tables.isNotEmpty())
    return databaseScheme.tables[random.nextInt(databaseScheme.tables.size)]
  }

  private fun selectRandomForeignKey(random: Random, tableScheme: TableScheme): ForeignKeyScheme {
    require(tableScheme.foreignKeys.isNotEmpty())
    return tableScheme.foreignKeys[random.nextInt(tableScheme.foreignKeys.size)]
  }

  private fun selectRandomProperty(
    random: Random,
    tableScheme: TableScheme,
    exclude: Array<PropertyScheme> = emptyArray()
  ): PropertyScheme {
    require(tableScheme.properties.size - exclude.size > 0)
    val arrayOfAvailableProperties = tableScheme.properties - exclude
    return arrayOfAvailableProperties[random.nextInt(arrayOfAvailableProperties.size)]
  }
}