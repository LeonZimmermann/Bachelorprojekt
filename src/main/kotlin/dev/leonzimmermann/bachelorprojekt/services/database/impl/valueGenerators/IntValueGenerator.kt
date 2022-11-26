package dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.Datatype
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyValueGenerator
import kotlin.random.Random
import kotlin.random.nextInt

internal class IntValueGenerator(val range: IntRange): PropertyValueGenerator {
  override val datatype: Datatype
    get() = Datatype.INTEGER

  override fun generateValue(random: Random): String = random.nextInt(range).toString()
}