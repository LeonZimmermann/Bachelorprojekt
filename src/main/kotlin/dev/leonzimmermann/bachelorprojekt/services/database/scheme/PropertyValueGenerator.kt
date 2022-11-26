package dev.leonzimmermann.bachelorprojekt.services.database.scheme

import kotlin.random.Random
import kotlin.random.nextInt

interface PropertyValueGenerator {
  val datatype: Datatype
  fun generateValue(random: Random): String
}