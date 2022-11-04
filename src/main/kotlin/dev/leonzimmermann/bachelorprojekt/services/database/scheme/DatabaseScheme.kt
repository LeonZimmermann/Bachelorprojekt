package dev.leonzimmermann.bachelorprojekt.services.database.scheme

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
}
