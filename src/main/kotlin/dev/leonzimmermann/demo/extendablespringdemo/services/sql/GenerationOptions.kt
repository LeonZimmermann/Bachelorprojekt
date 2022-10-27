package dev.leonzimmermann.demo.extendablespringdemo.services.sql

import kotlin.random.Random

data class GenerationOptions(
  val random: Random,
  val possibleNumberOfParameters: IntRange
)
