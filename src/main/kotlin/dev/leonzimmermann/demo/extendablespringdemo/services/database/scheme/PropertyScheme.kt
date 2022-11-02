package dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme

data class PropertyScheme(
  val name: String, val valueGenerator: PropertyValueGenerator = EmptyValueGenerator
) {
  val datatype: Datatype
    get() = valueGenerator.datatype

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as PropertyScheme

    if (name != other.name) return false
    if (datatype != other.datatype) return false

    return true
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }


}