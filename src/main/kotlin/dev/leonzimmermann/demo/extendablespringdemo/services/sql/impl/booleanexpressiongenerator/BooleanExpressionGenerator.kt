package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl.booleanexpressiongenerator

import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.BooleanExpression
import kotlin.random.Random

interface BooleanExpressionGenerator {
  fun generateBooleanExpression(): BooleanExpression
}