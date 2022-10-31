package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.ForeignKeyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.TableScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.GenerationOptions
import kotlin.random.nextInt

class PropertySelectionPathGenerator(
  private val databaseScheme: DatabaseScheme,
  private val generationOptions: GenerationOptions
) {

  // TODO This algorithm is traversing the tables in depth, but they should be traversed in breadth
  fun createPropertySelectionPaths(startingTable: TableScheme): Array<PropertySelectionPath> {
    var currentTable = startingTable
    var numberOfPropertiesToSelect =
      generationOptions.random.nextInt(generationOptions.possibleNumberOfParameters)
    val propertySelectionPaths = mutableListOf<PropertySelectionPath>()
    while (numberOfPropertiesToSelect > 0) {

      val numberOfPropertiesToSelectForTable = determineNumberOfPropertiesToSelectForTable(
        currentTable,
        numberOfPropertiesToSelect
      )
      numberOfPropertiesToSelect -= numberOfPropertiesToSelectForTable

      if (shouldLookUpNextTable(currentTable, numberOfPropertiesToSelect)) {
        findNextTableAndAddCurrentTableToPropertySelectionPath(
          propertySelectionPaths,
          currentTable,
          numberOfPropertiesToSelectForTable
        )?.let { currentTable = it }
      } else {
        addCurrentTableToPropertySelectionPath(
          propertySelectionPaths,
          currentTable,
          numberOfPropertiesToSelectForTable,
          null
        )
      }
    }
    return propertySelectionPaths.toTypedArray()
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