package dev.leonzimmermann.bachelorprojekt.services.sql.impl.booleanexpressiongenerator

import dev.leonzimmermann.bachelorprojekt.services.sql.model.BooleanExpression

internal interface BooleanExpressionGenerator {
  fun generateBooleanExpression(): BooleanExpression
}