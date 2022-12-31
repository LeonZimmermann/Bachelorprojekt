package dev.leonzimmermann.bachelorprojekt.services.persistence.impl

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.bachelorprojekt.services.persistence.PersistenceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.name
import kotlin.streams.toList

@Service
class PersistenceServiceImpl : PersistenceService {

  override suspend fun saveOntologyToDisk(fileName: String, ontology: OntModel) =
    withContext(Dispatchers.IO) {
      createDirIfItDoesntExist()
      val file = File("$DIRECTORY$fileName.xml")
      throwExceptionIfAlreadyExists(file, fileName)
      val fileCreated = file.createNewFile()
      if (fileCreated) {
        file.outputStream().use {
          ontology.write(it)
        }
      }
      fileCreated
    }

  override suspend fun saveDatabaseSchemeToDisk(fileName: String, databaseScheme: DatabaseScheme) =
    withContext(Dispatchers.IO) {
      createDirIfItDoesntExist()
      val file = File("$DIRECTORY$fileName.json")
      throwExceptionIfAlreadyExists(file, fileName)
      var successful = file.createNewFile()
      if (successful) {
        ObjectOutputStream(file.outputStream()).use {
          try {
            it.writeObject(databaseScheme)
          } catch (e: IOException) {
            removeFile(file.name)
            successful = false
          }
        }
      }
      successful
    }

  override suspend fun saveSQLToDisk(fileName: String, queries: List<String>) =
    withContext(Dispatchers.IO) {
      createDirIfItDoesntExist()
      val file = File("$DIRECTORY$fileName.sql")
      throwExceptionIfAlreadyExists(file, fileName)
      val fileCreated = file.createNewFile()
      if (fileCreated) {
        file.writeText(queries.joinToString(QUERY_SEPERATOR))
      }
      fileCreated
    }

  override suspend fun loadOntologyFromDisk(fileName: String) = withContext(Dispatchers.IO) {
    val file = File("$DIRECTORY$fileName.xml")
    throwExceptionIfFileDoesntExist(file, fileName)
    ModelFactory.createOntologyModel(
      OntModelSpec(OntModelSpec.OWL_MEM),
      RDFDataMgr.loadModel(file.path)
    )
  }

  override suspend fun loadDatabaseSchemeFromDisk(fileName: String) = withContext(Dispatchers.IO) {
    val file = File("$DIRECTORY$fileName.sql")
    throwExceptionIfFileDoesntExist(file, fileName)
    ObjectInputStream(file.inputStream()).use {
      it.readObject() as DatabaseScheme
    }
  }

  override suspend fun loadSQLFromDisk(fileName: String) = withContext(Dispatchers.IO) {
    val file = File("$DIRECTORY$fileName.sql")
    throwExceptionIfFileDoesntExist(file, fileName)
    file.readLines().joinToString().split(QUERY_SEPERATOR)
  }

  override suspend fun removeFile(fileName: String) = withContext(Dispatchers.IO) {
    val file = File("$DIRECTORY$fileName")
    throwExceptionIfFileDoesntExist(file, fileName)
    file.delete()
  }

  override suspend fun listFiles() = withContext(Dispatchers.IO) {
    Files.list(Path(DIRECTORY)).toList().map { it.fileName.name }
  }

  private suspend fun throwExceptionIfAlreadyExists(file: File, fileName: String) =
    withContext(Dispatchers.IO) {
      if (file.exists()) {
        throw IllegalArgumentException(
          "File $fileName already exists! List of files: " +
              listFiles().joinToString(", ")
        )
      }
    }

  private suspend fun throwExceptionIfFileDoesntExist(file: File, fileName: String) =
    withContext(Dispatchers.IO) {
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