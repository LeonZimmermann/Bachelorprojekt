package dev.leonzimmermann.demo.extendablespringdemo.services.sql.model

class SQLPropertyEnumeration(vararg elements: Pair<String, String?>) : SQLEnumeration<SQLProperty>(
  *elements.mapIndexed { index, value ->
    SQLProperty(
      value.first,
      value.second,
      plural = true,
      withSpecifier = index == 0
    )
  }.toTypedArray()
) {
}