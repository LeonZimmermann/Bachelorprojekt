package dev.leonzimmermann.bachelorprojekt.services.database.impl

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.TableScheme
import kotlin.random.Random

class DatabaseTableValueInserter(private val random: Random) {

    fun createInsertStatements(databaseScheme: DatabaseScheme, numberOfRows: Int): Array<String> =
        databaseScheme.tables
            .sortedBy { it.foreignKeys.size }
            .map { table -> table to createRowsForTable(table, numberOfRows) }
            .map { createInsertStatements(it.first, it.second) }
            .map { it.joinToString("\n") }
            .toTypedArray()

    private fun createRowsForTable(
        table: TableScheme,
        numberOfRows: Int
    ): Array<Array<String>> =
        (0 until numberOfRows).map { createRowForTable(table, numberOfRows) }.toTypedArray()

    private fun createRowForTable(table: TableScheme, numberOfRows: Int): Array<String> =
        createPrimaryKeyArray(table) + createPropertyArray(table) + createForeignKeyArray(table, numberOfRows)

    private fun createPrimaryKeyArray(table: TableScheme): Array<String> =
        arrayOf(table.primaryKey.generateValue(random))

    private fun createPropertyArray(table: TableScheme) =
        table.properties.map { property -> property.valueGenerator.generateValue(random) }
            .toTypedArray()

    // TODO Check for circular dependencies in Foreign Keys
    private fun createForeignKeyArray(table: TableScheme, numberOfRows: Int): Array<String> =
        table.foreignKeys.map { random.nextInt(numberOfRows).toString() }.toTypedArray()

    private fun createInsertStatements(
        table: TableScheme,
        values: Array<Array<String>>
    ): Array<String> = values.map { row ->
        createInsertStatement(
            table.name,
            getColumnNames(table), row
        )
    }.toTypedArray()

    private fun getColumnNames(table: TableScheme): Array<String> =
        table.properties.map { it.name }.toTypedArray() +
                table.foreignKeys.map { it.propertyName }.toTypedArray()

    private fun createInsertStatement(
        tableName: String,
        columnNames: Array<String>,
        values: Array<String>
    ): String = "INSERT INTO $tableName(${columnNames.joinToString(", ")})\n" +
            "VALUES(${values.joinToString(", ")});"
}
