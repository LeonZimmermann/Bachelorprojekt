package dev.leonzimmermann.bachelorprojekt.services.sql.impl.booleanexpressiongenerator

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.Datatype
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyScheme
import dev.leonzimmermann.bachelorprojekt.services.sql.model.AndExpression
import dev.leonzimmermann.bachelorprojekt.services.sql.model.EqualsExpression
import kotlin.random.Random

internal class BooleanExpressionGeneratorFactory {

  // TODO Add other BooleanExpressionGenerator classes
  fun getBooleanExpressionGenerator(random: Random, propertySchemes: Array<PropertyScheme>): BooleanExpressionGenerator {
    require(propertySchemes.isNotEmpty())
    return if (getPropertySchemesWithIntegerType(propertySchemes).isNotEmpty()) {
      RangeExpressionGenerator(random, getPropertySchemesWithIntegerType(propertySchemes))
    } else {
      when (random.nextInt(2)) {
        1 -> AndExpressionGenerator(random, propertySchemes)
        else -> EqualsExpressionGenerator(random, propertySchemes)
      }

    }
  }

  private fun getPropertySchemesWithIntegerType(propertySchemes: Array<PropertyScheme>) =
    propertySchemes.filter { it.datatype == Datatype.INTEGER }.toTypedArray()
}
