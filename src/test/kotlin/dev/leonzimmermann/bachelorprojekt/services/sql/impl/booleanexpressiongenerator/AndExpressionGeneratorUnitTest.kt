package dev.leonzimmermann.bachelorprojekt.services.sql.impl.booleanexpressiongenerator

import dev.leonzimmermann.bachelorprojekt.getAddressPropertySchemes
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.random.Random

class AndExpressionGeneratorUnitTest {
    @Test
    fun testGenerateBooleanExpression() {
        assertThat(AndExpressionGenerator(Random(1000), getAddressPropertySchemes()).generateBooleanExpression().toSQLString())
            .matches { "\\(.*?\\)+[AND ]*".toRegex().matches(it) }
    }
}
