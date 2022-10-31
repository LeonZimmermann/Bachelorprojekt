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
    val propertySelectionPaths = PropertySelectionPathGenerator(databaseScheme, generationOptions)
      .createPropertySelectionPaths(startingTable)
    return SelectStatement(
      selectProperties = selectRandomPropertiesUsingPropertySelectionPaths(
        propertySelectionPaths,
        generationOptions
      ),
      fromStatement = FromStatement(SQLTable(startingTable.name)),
      joinExpressions = createJoinExpressions(propertySelectionPaths)
    )
  }

  private fun selectRandomPropertiesUsingPropertySelectionPaths(
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