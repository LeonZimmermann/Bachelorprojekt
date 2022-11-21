package dev.leonzimmermann.bachelorprojekt.services.database.scheme

enum class Datatype(val databaseIdentifier: String) {
  STRING("VARCHAR"),
  INTEGER("INT"),
  LONG("LONG");
}
