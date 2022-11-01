package dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme

// TODO Change values property to a value generation method
data class PropertyScheme(
  val name: String, val datatype: String, val values: Array<String> = emptyArray()
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as PropertyScheme

    if (name != other.name) return false
    if (datatype != other.datatype) return false
    if (!values.contentEquals(other.values)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = name.hashCode()
    result = 31 * result + datatype.hashCode()
    result = 31 * result + values.contentHashCode()
    return result
  }
}