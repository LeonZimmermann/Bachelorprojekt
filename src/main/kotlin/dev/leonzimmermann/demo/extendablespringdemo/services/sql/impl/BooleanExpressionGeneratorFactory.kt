package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.Datatype
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import kotlin.random.Random

class BooleanExpressionGeneratorFactory {

  // TODO Add other BooleanExpressionGenerator classes
  fun getBooleanExpressionGenerator(random: Random, propertySchemes: Array<PropertyScheme>): BooleanExpressionGenerator {
    require(propertySchemes.isNotEmpty())
    return if (getPropertySchemesWithIntegerType(propertySchemes).isNotEmpty()) {
      RangeExpressionGenerator(random, getPropertySchemesWithIntegerType(propertySchemes))
    } else {
      EqualsExpressionGenerator(random, propertySchemes)
    }
  }

  private fun getPropertySchemesWithIntegerType(propertySchemes: Array<PropertyScheme>) =
    propertySchemes.filter { it.datatype == Datatype.INTEGER }.toTypedArray()
}