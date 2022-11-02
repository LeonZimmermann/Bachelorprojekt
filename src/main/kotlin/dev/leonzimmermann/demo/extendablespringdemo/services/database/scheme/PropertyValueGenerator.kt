package dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme

import kotlin.random.Random
import kotlin.random.nextInt

sealed interface PropertyValueGenerator {
  val datatype: Datatype
  fun generateValue(random: Random): String
}

object EmptyValueGenerator : PropertyValueGenerator {
  override val datatype: Datatype
    get() = Datatype.STRING

  override fun generateValue(random: Random): String = ""
}

class ObjectIdGenerator: PropertyValueGenerator {
  private var nextObjectId: Long = 0

  override val datatype: Datatype
    get() = Datatype.LONG

  override fun generateValue(random: Random): String {
    val result = nextObjectId
    nextObjectId++
    return result.toString()
  }
}

class IntValueGenerator(private val range: IntRange): PropertyValueGenerator {
  override val datatype: Datatype
    get() = Datatype.INTEGER

  override fun generateValue(random: Random): String = random.nextInt(range).toString()
}

class ValueGeneratorFromStringList(private vararg val values: String): PropertyValueGenerator {
  override val datatype: Datatype
    get() = Datatype.STRING

  override fun generateValue(random: Random): String = values[random.nextInt(values.size)]
}