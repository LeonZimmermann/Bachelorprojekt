package dev.leonzimmermann.bachelorprojekt.services.sql.impl.booleanexpressiongenerator

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyScheme
import dev.leonzimmermann.bachelorprojekt.services.sql.model.AndExpression
import dev.leonzimmermann.bachelorprojekt.services.sql.model.BooleanExpression
import kotlin.random.Random

class AndExpressionGenerator(
    private val random: Random,
    private val propertySchemes: Array<PropertyScheme>,
) : BooleanExpressionGenerator {
    override fun generateBooleanExpression(): BooleanExpression {
        return AndExpression(*arrayOfNulls<BooleanExpressionGenerator>(random.nextInt(3)).map {
            EqualsExpressionGenerator(random, propertySchemes).generateBooleanExpression()
        }.toTypedArray())
    }
}
