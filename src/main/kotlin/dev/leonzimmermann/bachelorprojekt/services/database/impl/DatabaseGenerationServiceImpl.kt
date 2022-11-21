package dev.leonzimmermann.bachelorprojekt.services.database.impl

import dev.leonzimmermann.bachelorprojekt.services.database.DatabaseGenerationService
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.Datatype
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.ForeignKeyScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyScheme
import org.springframework.stereotype.Service

@Service
class DatabaseGenerationServiceImpl : DatabaseGenerationService {

    // TODO Add Value-Constraints
    override fun getDatabaseGenerationQueriesForScheme(databaseScheme: DatabaseScheme): Array<String> {
        return databaseScheme.tables.map {
            """
            |CREATE TABLE ${it.name}(
            |objectId $INTEGER $NOT_NULL,
            |${mapArrayToQuery(it.properties, this::mapPropertySchemeToQuery)},
            |${mapArrayToQuery(it.foreignKeys, this::mapForeignKeySchemeToQuery)},
            |${mapArrayToQuery(it.foreignKeys, this::mapForeignKeySchemeToReferenceQuery)},
            |$PRIMARY_KEY(objectId));
            |""".trimMargin()
        }.toTypedArray()
    }

    private fun mapPropertySchemeToQuery(propertyScheme: PropertyScheme): String =
        "${propertyScheme.name} ${propertyScheme.datatype.databaseIdentifier} $NOT_NULL"

    private fun mapForeignKeySchemeToQuery(foreignKeyScheme: ForeignKeyScheme): String =
        "${foreignKeyScheme.propertyName} $INTEGER $NOT_NULL"

    private fun mapForeignKeySchemeToReferenceQuery(foreignKeyScheme: ForeignKeyScheme): String =
        "FOREIGN KEY(${foreignKeyScheme.propertyName}) REFERENCES ${foreignKeyScheme.referenceTable}(${foreignKeyScheme.referenceField})"

    private fun <T> mapArrayToQuery(array: Array<T>, mapper: (T) -> String): String =
        array.joinToString(",\n", transform = mapper)

    companion object {
        private const val PRIMARY_KEY = "PRIMARY KEY"
        private const val NOT_NULL = "NOT NULL"
        private val INTEGER: String = Datatype.INTEGER.databaseIdentifier
    }
}
