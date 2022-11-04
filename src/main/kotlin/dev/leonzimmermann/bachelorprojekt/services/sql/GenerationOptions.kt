package dev.leonzimmermann.bachelorprojekt.services.sql

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.TableScheme
import kotlin.random.Random

data class GenerationOptions(
  val random: Random,
  val possibleNumberOfParameters: IntRange,
  val startingPoint: TableScheme? = null,
  val enableWhereClause: Boolean = true
)
