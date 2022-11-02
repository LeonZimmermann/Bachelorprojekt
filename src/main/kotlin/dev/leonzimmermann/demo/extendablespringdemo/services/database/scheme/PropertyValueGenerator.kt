package dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme

import kotlin.random.Random
import kotlin.random.nextInt

sealed interface PropertyValueGenerator {
  fun generateValue(random: Random): String
}

object EmptyValueGenerator : PropertyValueGenerator {
  override fun generateValue(random: Random): String = ""
}

class IntValueGenerator(private val range: IntRange): PropertyValueGenerator {
  override fun generateValue(random: Random): String = random.nextInt(range).toString()
}

class ValueGeneratorFromList(private vararg val values: String): PropertyValueGenerator {
  override fun generateValue(random: Random): String = values[random.nextInt(values.size)]
}