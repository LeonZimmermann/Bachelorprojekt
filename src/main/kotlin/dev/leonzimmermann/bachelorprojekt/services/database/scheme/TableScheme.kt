package dev.leonzimmermann.bachelorprojekt.services.database.scheme

/**
 * The primaryKey is not contained in the properties array, just in the primaryKey field.
 */
data class TableScheme(
  val name: String,
  val primaryKey: PropertyScheme,
  val foreignKeys: Array<ForeignKeyScheme>,
  val properties: Array<PropertyScheme>,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TableScheme

    if (name != other.name) return false
    if (primaryKey != other.primaryKey) return false
    if (!properties.contentEquals(other.properties)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = name.hashCode()
    result = 31 * result + primaryKey.hashCode()
    result = 31 * result + properties.contentHashCode()
    return result
  }
}