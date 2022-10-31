package dev.leonzimmermann.demo.extendablespringdemo.services.sql

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.TableScheme
import kotlin.random.Random

data class GenerationOptions(
  val random: Random,
  val possibleNumberOfParameters: IntRange,
  val startingPoint: TableScheme? = null
)
