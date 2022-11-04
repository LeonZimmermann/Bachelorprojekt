package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl.booleanexpressiongenerator

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.Datatype
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.EmptyValueGenerator
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.*
import kotlin.random.Random

class EqualsExpressionGenerator(
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
      Datatype.INTEGER -> SQLNumberLiteral(propertyScheme.generateValue(random).toInt())
      Datatype.LONG -> SQLNumberLiteral(
        propertyScheme.generateValue(random).toInt()
      )      // TODO Maybe change, to enable long?
    }
  }
}