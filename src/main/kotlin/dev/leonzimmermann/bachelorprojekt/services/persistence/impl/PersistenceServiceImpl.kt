package dev.leonzimmermann.bachelorprojekt.services.persistence.impl

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.bachelorprojekt.services.persistence.PersistenceService
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.springframework.stereotype.Service
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.name
import kotlin.streams.toList

@Service
class PersistenceServiceImpl: PersistenceService {

  override fun saveOntologyToDisk(fileName: String, ontology: OntModel) {
    createDirIfItDoesntExist()
    val file = File("$DIRECTORY$fileName.xml")
    throwExceptionIfAlreadyExists(file, fileName)
    file.createNewFile()
    file.outputStream().use {
      ontology.write(it)
    }
  }

  override fun saveDatabaseSchemeToDisk(fileName: String, databaseScheme: DatabaseScheme) {
    createDirIfItDoesntExist()
    val file = File(DIRECTORY + fileName)
    throwExceptionIfAlreadyExists(file, "$fileName.dbscheme")
    file.createNewFile()
    ObjectOutputStream(file.outputStream()).use {
      it.writeObject(databaseScheme)
    }
  }

  override fun saveSQLToDisk(fileName: String, queries: List<String>) {
    createDirIfItDoesntExist()
    val file = File("$DIRECTORY$fileName.sql")
    throwExceptionIfAlreadyExists(file, fileName)
    file.createNewFile()
    file.writeText(queries.joinToString(QUERY_SEPERATOR))
  }

  override fun loadOntologyFromDisk(fileName: String): OntModel {
    val file = File("$DIRECTORY$fileName.xml")
    throwExceptionIfFileDoesntExist(file, fileName)
    return ModelFactory.createOntologyModel(
      OntModelSpec(OntModelSpec.OWL_MEM),
      RDFDataMgr.loadModel(file.path)
    )
  }

  override fun loadDatabaseSchemeFromDisk(fileName: String): DatabaseScheme {
    val file = File("$DIRECTORY$fileName.sql")
    throwExceptionIfFileDoesntExist(file, fileName)
    ObjectInputStream(file.inputStream()).use {
      return it.readObject() as DatabaseScheme
    }
  }

  override fun loadSQLFromDisk(fileName: String): List<String> {
    val file = File("$DIRECTORY$fileName.sql")
    throwExceptionIfFileDoesntExist(file, fileName)
    return file.readLines().joinToString().split(QUERY_SEPERATOR)
  }

  override fun listFiles(): List<String> {
    return Files.list(Path(DIRECTORY)).toList().map { it.fileName.name }
  }

  private fun throwExceptionIfAlreadyExists(file: File, fileName: String) {
    if (file.exists()) {
      throw IllegalArgumentException(
        "File $fileName already exists! List of files: " +
            listFiles().joinToString(", "))
    }
  }

  private fun throwExceptionIfFileDoesntExist(file: File, fileName: String) {
    if (!file.exists()) {
      throw IllegalArgumentException(
        "File $fileName was not found! List of files: " +
            listFiles().joinToString(", ")
      )
    }
  }

  private fun createDirIfItDoesntExist() {
    if (Files.notExists(Path(DIRECTORY))) {
      Files.createDirectory(Path(DIRECTORY))
    }
  }

  companion object {
    private const val DIRECTORY = "./persistence/"
    private const val QUERY_SEPERATOR = "\n\n"
  }

}