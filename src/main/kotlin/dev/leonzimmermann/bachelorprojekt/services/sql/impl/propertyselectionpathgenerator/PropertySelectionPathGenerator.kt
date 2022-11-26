package dev.leonzimmermann.bachelorprojekt.services.sql.impl.propertyselectionpathgenerator

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.ForeignKeyScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.TableScheme
import dev.leonzimmermann.bachelorprojekt.usecases.assignment.GenerationOptions
import kotlin.random.nextInt

internal class PropertySelectionPathGenerator(
  private val databaseScheme: DatabaseScheme,
  private val generationOptions: GenerationOptions
) {

  // TODO This algorithm is traversing the tables in depth, but they should be traversed in breadth
  fun createPropertySelectionPaths(startingTable: TableScheme): Array<PropertySelectionPath> {
    var currentTable: TableScheme? = startingTable
    var numberOfPropertiesToSelect =
      generationOptions.random.nextInt(generationOptions.possibleNumberOfParameters)
    val propertySelectionPaths = mutableListOf<PropertySelectionPath>()
    while (currentTable != null && numberOfPropertiesToSelect > 0) {

      val numberOfPropertiesToSelectForTable = determineNumberOfPropertiesToSelectForTable(
        currentTable,
        numberOfPropertiesToSelect
      )
      numberOfPropertiesToSelect -= numberOfPropertiesToSelectForTable

      currentTable = traverseOneStep(
        currentTable,
        numberOfPropertiesToSelect,
        propertySelectionPaths,
        numberOfPropertiesToSelectForTable
      )
    }
    return propertySelectionPaths.toTypedArray()
  }

  private fun traverseOneStep(
    currentTable: TableScheme,
    numberOfPropertiesToSelect: Int,
    propertySelectionPaths: MutableList<PropertySelectionPath>,
    numberOfPropertiesToSelectForTable: Int
  ): TableScheme? {
    return if (shouldLookUpNextTable(currentTable, numberOfPropertiesToSelect)) {
      findNextTableAndAddCurrentTableToPropertySelectionPath(
        propertySelectionPaths,
        currentTable,
        numberOfPropertiesToSelectForTable
      )
    } else {
      addCurrentTableToPropertySelectionPath(
        propertySelectionPaths,
        currentTable,
        numberOfPropertiesToSelectForTable,
        null
      )
      currentTable
    }
  }

  private fun shouldLookUpNextTable(currentTable: TableScheme, numberOfPropertiesToSelect: Int): Boolean =
    numberOfPropertiesToSelect > 0 && currentTable.foreignKeys.isNotEmpty()

  private fun findNextTableAndAddCurrentTableToPropertySelectionPath(
    propertySelectionPaths: MutableList<PropertySelectionPath>,
    currentTable: TableScheme,
    numberOfPropertiesToSelectForTable: Int
  ): TableScheme? {
    val foreignKey = selectRandomForeignKey(currentTable)
    val nextTable = databaseScheme.tables.find { it.name == foreignKey.referenceTable }
    if (nextTable != null) {
      addCurrentTableToPropertySelectionPath(
        propertySelectionPaths,
        currentTable,
        numberOfPropertiesToSelectForTable,
        foreignKey
      )
    }
    return nextTable
  }

  private fun addCurrentTableToPropertySelectionPath(
    propertySelectionPaths: MutableList<PropertySelectionPath>,
    currentTable: TableScheme,
    numberOfPropertiesToSelectForTable: Int,
    foreignKey: ForeignKeyScheme?
  ) {
    propertySelectionPaths += PropertySelectionPath(
      currentTable,
      numberOfPropertiesToSelectForTable,
      foreignKey
    )
  }

  private fun determineNumberOfPropertiesToSelectForTable(
    tableScheme: TableScheme,
    numberOfPropertiesToSelect: Int
  ): Int {
    return Integer.min(tableScheme.properties.size, numberOfPropertiesToSelect)
  }

  private fun selectRandomForeignKey(tableScheme: TableScheme): ForeignKeyScheme {
    require(tableScheme.foreignKeys.isNotEmpty())
    return tableScheme.foreignKeys[generationOptions.random.nextInt(tableScheme.foreignKeys.size)]
  }
}