package dev.leonzimmermann.bachelorprojekt.services.database.impl

import dev.leonzimmermann.bachelorprojekt.generation.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.*
import org.springframework.stereotype.Service

@Service
internal class DatabaseGenerationServiceImpl : DatabaseGenerationService {

  // TODO Add Value-Constraints
  override fun getDatabaseGenerationQueriesForScheme(databaseScheme: DatabaseScheme): Array<String> {
    return databaseScheme.tables.map(this::mapTableSchemeToQuery).toTypedArray()
  }

  private fun mapTableSchemeToQuery(tableScheme: TableScheme): String {
    val builder = StringBuilder()
    builder.appendLine("CREATE TABLE ${tableScheme.name}(")
    builder.appendLine(
      mapTableSchemeToInitializationList(tableScheme).joinToString(
        separator = ",\n",
        postfix = ","
      )
    )
    builder.appendLine("$PRIMARY_KEY(objectId));")
    return builder.toString()
  }

  private fun mapTableSchemeToInitializationList(tableScheme: TableScheme): MutableList<String> {
    val initializationList = mutableListOf<String>()
    initializationList += "objectId $INTEGER $NOT_NULL"
    initializationList += mapArrayToQuery(
      tableScheme.properties,
      this::mapPropertySchemeToQuery
    )
    initializationList += mapArrayToQuery(
      tableScheme.foreignKeys,
      this::mapForeignKeySchemeToQuery
    )
    initializationList += mapArrayToQuery(
      tableScheme.foreignKeys,
      this::mapForeignKeySchemeToReferenceQuery
    )
    return initializationList
  }

  private fun mapPropertySchemeToQuery(propertyScheme: PropertyScheme): String =
    "${propertyScheme.name} ${propertyScheme.datatype.databaseIdentifier} $NOT_NULL"

  private fun mapForeignKeySchemeToQuery(foreignKeyScheme: ForeignKeyScheme): String =
    "${foreignKeyScheme.propertyName} $INTEGER $NOT_NULL"

  private fun mapForeignKeySchemeToReferenceQuery(foreignKeyScheme: ForeignKeyScheme): String =
    "FOREIGN KEY(${foreignKeyScheme.propertyName}) REFERENCES ${foreignKeyScheme.referenceTable}(${foreignKeyScheme.referenceField})"

  private fun <T> mapArrayToQuery(array: Array<T>, mapper: (T) -> String): Array<String> {
    return array.map(mapper).toTypedArray()
  }

  companion object {
    private const val PRIMARY_KEY = "PRIMARY KEY"
    private const val NOT_NULL = "NOT NULL"
    private val INTEGER: String = Datatype.INTEGER.databaseIdentifier
  }
}
