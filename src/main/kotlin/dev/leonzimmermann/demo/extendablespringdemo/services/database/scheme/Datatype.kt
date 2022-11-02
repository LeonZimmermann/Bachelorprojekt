package dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme

enum class Datatype(private val databaseIdentifier: String) {
  STRING("VARCHAR"),
  INTEGER("INT"),
  LONG("LONG")
}