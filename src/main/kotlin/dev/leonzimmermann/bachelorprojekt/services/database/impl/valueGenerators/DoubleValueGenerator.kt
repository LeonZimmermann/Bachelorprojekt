package dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.Datatype
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyValueGenerator
import kotlin.random.Random
import kotlin.random.nextInt

internal class DoubleValueGenerator(val range: IntRange) : PropertyValueGenerator {
  override val datatype: Datatype
    get() = Datatype.FLOATING_POINT

  override fun generateValue(random: Random): String =
    random.nextDouble(range.first.toDouble(), range.last.toDouble()).toString()
}