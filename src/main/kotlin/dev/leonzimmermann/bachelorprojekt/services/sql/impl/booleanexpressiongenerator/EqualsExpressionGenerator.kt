package dev.leonzimmermann.bachelorprojekt.services.sql.impl.booleanexpressiongenerator

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.Datatype
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyScheme
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.EmptyValueGenerator
import dev.leonzimmermann.bachelorprojekt.services.sql.model.*
import kotlin.random.Random

internal class EqualsExpressionGenerator(
  private val random: Random,
  private val propertySchemes: Array<PropertyScheme>
) : BooleanExpressionGenerator, RandomPropertySelectionMixin {
  override fun generateBooleanExpression(): BooleanExpression {
    val propertyScheme = selectRandomPropertySchemeFromArray(random, propertySchemes)
    return EqualsExpression(
      BooleanExpressionProperty(SQLProperty(propertyScheme.name)),
      BooleanExpressionLiteral(selectRandomValueForProperty(random, propertyScheme))
    )
  }

  private fun selectRandomValueForProperty(
    random: Random,
    propertyScheme: PropertyScheme
  ): SQLLiteral {
    require(propertyScheme.valueGenerator != EmptyValueGenerator)
    return when (propertyScheme.datatype) {
      Datatype.STRING -> SQLStringLiteral(propertyScheme.generateValue(random))
      Datatype.INTEGER -> SQLNumberLiteral(propertyScheme.generateValue(random))
      Datatype.FLOAT -> SQLNumberLiteral(propertyScheme.generateValue(random))
      Datatype.LONG -> SQLNumberLiteral(propertyScheme.generateValue(random))
      Datatype.BOOLEAN -> SQLNumberLiteral(propertyScheme.generateValue(random))
    }
  }
}
