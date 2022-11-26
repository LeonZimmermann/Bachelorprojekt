package dev.leonzimmermann.bachelorprojekt.services.sql.impl.propertyselectionpathgenerator

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.ForeignKeyScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.TableScheme

internal data class PropertySelectionPath(
  val tableScheme: TableScheme,
  val numberOfPropertiesToSelect: Int,
  val foreignKey: ForeignKeyScheme? = null,
)