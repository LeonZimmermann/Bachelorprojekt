package dev.leonzimmermann.bachelorprojekt.services.database.scheme

import com.google.gson.Gson
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.PropertyValueGeneratorAdapter

data class DatabaseScheme(val tables: Array<TableScheme>) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as DatabaseScheme

    if (!tables.contentEquals(other.tables)) return false

    return true
  }

  override fun hashCode(): Int {
    return tables.contentHashCode()
  }

  fun getTableForName(tableName: String): TableScheme = tables.find { it.name == tableName }!!

  fun toJson(): String = Gson().newBuilder()
    .registerTypeAdapter(PropertyValueGenerator::class.java, PropertyValueGeneratorAdapter())
    .create()
    .toJson(this)

  companion object {
    fun fromJson(json: String): DatabaseScheme = Gson().newBuilder()
      .registerTypeAdapter(PropertyValueGenerator::class.java, PropertyValueGeneratorAdapter())
      .create()
      .fromJson(json, DatabaseScheme::class.java)
  }
}
