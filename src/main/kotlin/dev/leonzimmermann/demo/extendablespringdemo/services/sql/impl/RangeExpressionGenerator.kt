package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.Datatype
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.IntValueGenerator
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.*
import kotlin.random.Random
import kotlin.random.nextInt

class RangeExpressionGenerator(
  private val random: Random,
  private val propertySchemes: Array<PropertyScheme>
) : BooleanExpressionGenerator, RandomPropertySelectionMixin {

  init {
    require(propertySchemes.isNotEmpty())
    require(propertySchemes.all { it.datatype == Datatype.INTEGER })
  }

  override fun generateBooleanExpression(): BooleanExpression {
    val propertyScheme = selectRandomPropertySchemeFromArray(random, propertySchemes)
    val range =
      getRandomSubRangeOfIntRange(getRangeOfPropertySchemeWithTypeInt(propertyScheme), random)
    return AndExpression(
      GreaterEqualsExpression(
        BooleanExpressionProperty(SQLProperty(propertyScheme.name)),
        BooleanExpressionLiteral(SQLNumberLiteral(range.first))
      ),
      SmallerEqualsExpression(
        BooleanExpressionProperty(SQLProperty(propertyScheme.name)),
        BooleanExpressionLiteral(SQLNumberLiteral(range.last))
      )
    )
  }

  private fun getRangeOfPropertySchemeWithTypeInt(propertyScheme: PropertyScheme): IntRange {
    require(propertyScheme.datatype == Datatype.INTEGER)
    return (propertyScheme.valueGenerator as IntValueGenerator).range
  }

  private fun getRandomSubRangeOfIntRange(intRange: IntRange, random: Random): IntRange {
    require(intRange.last - intRange.first > 3)
    val sizeOfIntRange = intRange.last - intRange.first
    val sizeOfSubRange = random.nextInt(IntRange(2, sizeOfIntRange - 1))
    val maximumOffset = sizeOfIntRange - sizeOfSubRange
    val offset = random.nextInt(IntRange(0, maximumOffset))
    return IntRange(offset, offset + sizeOfSubRange)
  }

}