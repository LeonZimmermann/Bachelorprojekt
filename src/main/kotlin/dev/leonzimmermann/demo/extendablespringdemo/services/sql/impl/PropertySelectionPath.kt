package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.ForeignKeyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.TableScheme

data class PropertySelectionPath(
  val tableScheme: TableScheme,
  val numberOfPropertiesToSelect: Int,
  val foreignKey: ForeignKeyScheme? = null,
)