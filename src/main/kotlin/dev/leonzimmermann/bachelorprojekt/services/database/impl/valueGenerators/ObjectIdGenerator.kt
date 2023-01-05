package dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.Datatype
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyValueGenerator
import kotlin.random.Random

internal class ObjectIdGenerator(): PropertyValueGenerator {
  var nextObjectId: Long = 0
    private set

  override val datatype: Datatype
    get() = Datatype.LONG

  constructor(nextObjectId: Long) : this() {
    this.nextObjectId = nextObjectId
  }

  override fun generateValue(random: Random): String {
    val result = nextObjectId
    nextObjectId++
    return result.toString()
  }
}
