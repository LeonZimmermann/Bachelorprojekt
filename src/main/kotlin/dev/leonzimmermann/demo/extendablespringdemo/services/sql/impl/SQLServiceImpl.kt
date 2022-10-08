package dev.leonzimmermann.demo.extendablespringdemo.services.sql.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.sql.SQLService
import dev.leonzimmermann.demo.extendablespringdemo.services.sql.model.*
import org.springframework.stereotype.Service

@Service
class SQLServiceImpl: SQLService {
  override fun generateSQLExpression(): SQLExpression {
    return SelectStatement(
      selectProperties = SQLEnumeration(SQLProperty("street")),
      fromStatement = FromStatement(SQLTable("Address")),
      whereClause = WhereClause(
        EqualsExpression(
          BooleanExpressionProperty(SQLProperty("city")),
          BooleanExpressionLiteral(SQLStringLiteral("Essen"))
        )
      )
    )
  }
}