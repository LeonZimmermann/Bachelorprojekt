package dev.leonzimmermann.bachelorprojekt.services.sql.impl.booleanexpressiongenerator

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyScheme
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