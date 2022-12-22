package dev.leonzimmermann.bachelorprojekt.services.database.scheme

enum class Datatype(val databaseIdentifier: String) {
  STRING("VARCHAR"),
  INTEGER("INT"),
  FLOAT("FLOAT(16)"),
  LONG("LONG");
}
