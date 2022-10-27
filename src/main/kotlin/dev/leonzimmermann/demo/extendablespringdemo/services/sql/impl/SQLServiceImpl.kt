package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.TableScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.SQLService
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.*
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class SQLServiceImpl(seed: Int = 1000): SQLService {

  private val random = Random(seed)

  override fun generateSQLExpression(databaseScheme: DatabaseScheme): SQLExpression {
    require(databaseScheme.tables.isNotEmpty())

    val startingTable = selectRandomTable(databaseScheme)

    val fromStatement = FromStatement(SQLTable(startingTable.name))
    val selectProperties = SQLProperty(selectRandomProperty(startingTable).name)

    return SelectStatement(
      selectProperties = SQLEnumeration(selectProperties),
      fromStatement = fromStatement,
    )
  }
  private fun selectRandomTable(databaseScheme: DatabaseScheme): TableScheme =
    databaseScheme.tables[random.nextInt(databaseScheme.tables.size)]

  private fun selectRandomProperty(tableScheme: TableScheme): PropertyScheme =
    tableScheme.properties[random.nextInt(tableScheme.properties.size)]
}