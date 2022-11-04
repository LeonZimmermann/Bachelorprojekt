package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl.booleanexpressiongenerator

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import kotlin.random.Random

interface RandomPropertySelectionMixin {
  fun selectRandomPropertySchemeFromArray(
    random: Random,
    propertySchemes: Array<PropertyScheme>
  ): PropertyScheme {
    require(propertySchemes.isNotEmpty())
    return propertySchemes[random.nextInt(propertySchemes.size)]
  }
}