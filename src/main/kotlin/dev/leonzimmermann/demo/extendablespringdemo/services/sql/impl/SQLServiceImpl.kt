package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.*
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
    val startingTable =
      generationOptions.startingPoint ?: selectRandomTable(generationOptions.random, databaseScheme)
    val propertySelectionPaths = PropertySelectionPathGenerator(databaseScheme, generationOptions)
      .createPropertySelectionPaths(startingTable)
    val selectedProperties = selectRandomPropertiesUsingPropertySelectionPaths(
      propertySelectionPaths,
      generationOptions
    )
    return SelectStatement(
      selectProperties = SQLEnumeration(*mapPropertySchemesToSQLProperties(selectedProperties)),
      fromStatement = FromStatement(SQLTable(startingTable.name)),
      joinExpressions = createJoinExpressions(propertySelectionPaths),
      whereClause = if (generationOptions.selectWithWhereClause) createWhereClause(
        generationOptions.random,
        selectedProperties
      ) else null
    )
  }

  private fun selectRandomPropertiesUsingPropertySelectionPaths(
    propertySelectionPaths: Array<PropertySelectionPath>,
    generationOptions: GenerationOptions
  ): Array<PropertyScheme> {
    return propertySelectionPaths.flatMap {
      selectFixedNumberOfRandomPropertiesFromTable(
        generationOptions.random,
        it.tableScheme,
        it.numberOfPropertiesToSelect
      ).toList()
    }.toTypedArray()
  }

  private fun mapPropertySchemesToSQLProperties(propertySchemes: Array<PropertyScheme>): Array<SQLProperty> =
    propertySchemes.map { SQLProperty(it.name) }
      .toTypedArray()

  private fun createJoinExpressions(propertySelectionPaths: Array<PropertySelectionPath>): Array<JoinExpression> {
    return propertySelectionPaths
      .filter { it.foreignKey != null }
      .map { mapPropertySelectionPathToJoinExpression(it) }
      .toTypedArray()
  }

  private fun createWhereClause(
    random: Random,
    propertySchemes: Array<PropertyScheme>
  ): WhereClause {
    return WhereClause(BooleanExpressionGeneratorFactory().getBooleanExpressionGenerator(random, propertySchemes)
      .generateBooleanExpression())
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