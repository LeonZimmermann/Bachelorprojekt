package dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme

data class TableScheme(
  val name: String,
  val properties: Array<PropertyScheme>
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TableScheme

    if (name != other.name) return false
    if (!properties.contentEquals(other.properties)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = name.hashCode()
    result = 31 * result + properties.contentHashCode()
    return result
  }
}