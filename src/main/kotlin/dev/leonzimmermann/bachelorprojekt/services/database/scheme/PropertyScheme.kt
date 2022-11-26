package dev.leonzimmermann.bachelorprojekt.services.database.scheme

import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.EmptyValueGenerator
import kotlin.random.Random

data class PropertyScheme(
  val name: String, val valueGenerator: PropertyValueGenerator = EmptyValueGenerator
) {
  val datatype: Datatype
    get() = valueGenerator.datatype

  fun generateValue(random: Random): String = valueGenerator.generateValue(random)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as PropertyScheme

    if (name != other.name) return false
    if (datatype != other.datatype) return false

    return true
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }


}