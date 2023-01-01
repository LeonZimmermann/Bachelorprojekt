package dev.leonzimmermann.bachelorprojekt.services.persistence.impl

import com.google.gson.Gson
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.bachelorprojekt.services.persistence.PersistenceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.name

@Service
class PersistenceServiceImpl : PersistenceService {

  private val logger: Logger = LoggerFactory.getLogger(javaClass)

  override suspend fun saveOntologyToDisk(fileName: String, ontology: OntModel): Boolean =
    withContext(Dispatchers.IO) {
      createDirIfItDoesntExist()
      val file = File("$DIRECTORY$fileName.xml")
      throwExceptionIfAlreadyExists(file, fileName)
      val fileCreated = file.createNewFile()
      if (fileCreated) {
        file.outputStream().use {
          ontology.write(it)
        }
        logger.debug("PersistenceServiceImpl: saved $fileName")
      }
      fileCreated
    }

  override suspend fun saveDatabaseSchemeToDisk(fileName: String, databaseScheme: DatabaseScheme): Boolean =
    withContext(Dispatchers.IO) {
      createDirIfItDoesntExist()
      val file = File("$DIRECTORY$fileName.json")
      throwExceptionIfAlreadyExists(file, fileName)
      try {
        if (file.createNewFile()) {
          file.writeText(Gson().toJson(databaseScheme))
          logger.debug("PersistenceServiceImpl: saved $fileName")
          true
        } else false
      } catch (e: Exception) {
        e.printStackTrace()
        logger.error("PersistenceServiceImpl: could not save $fileName. Removing file...")
        removeFile(file.name)
        false
      }
    }

  override suspend fun saveSQLToDisk(fileName: String, queries: List<String>): Boolean =
    withContext(Dispatchers.IO) {
      createDirIfItDoesntExist()
      val file = File("$DIRECTORY$fileName.sql")
      throwExceptionIfAlreadyExists(file, fileName)
      val fileCreated = file.createNewFile()
      if (fileCreated) {
        file.writeText(queries.joinToString(QUERY_SEPERATOR))
        logger.debug("PersistenceServiceImpl: saved $fileName")
      }
      fileCreated
    }

  override suspend fun loadOntologyFromDisk(fileName: String): OntModel = withContext(Dispatchers.IO) {
    val file = File("$DIRECTORY$fileName.xml")
    throwExceptionIfFileDoesntExist(file, fileName)
    logger.debug("PersistenceServiceImpl: loading $fileName")
    ModelFactory.createOntologyModel(
      OntModelSpec(OntModelSpec.OWL_MEM),
      RDFDataMgr.loadModel(file.absolutePath)
    )
  }

  override suspend fun loadDatabaseSchemeFromDisk(fileName: String): DatabaseScheme = withContext(Dispatchers.IO) {
    val file = File("$DIRECTORY$fileName.json")
    throwExceptionIfFileDoesntExist(file, fileName)
    logger.debug("PersistenceServiceImpl: loading $fileName")
    DatabaseScheme.fromJson(file.readText())
  }

  override suspend fun loadSQLFromDisk(fileName: String): List<String> = withContext(Dispatchers.IO) {
    val file = File("$DIRECTORY$fileName.sql")
    throwExceptionIfFileDoesntExist(file, fileName)
    logger.debug("PersistenceServiceImpl: loading $fileName")
    file.readLines().joinToString().split(QUERY_SEPERATOR)
  }

  override suspend fun removeFile(fileName: String) = withContext(Dispatchers.IO) {
    val file = File("$DIRECTORY$fileName")
    throwExceptionIfFileDoesntExist(file, fileName)
    logger.debug("PersistenceServiceImpl: removing file \"$fileName\"")
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
              listFiles().joinToString(", ").ifEmpty { "no files found" }
        )
      }
    }

  private suspend fun throwExceptionIfFileDoesntExist(file: File, fileName: String) =
    withContext(Dispatchers.IO) {
      if (!file.exists()) {
        throw IllegalArgumentException(
          "File $fileName was not found! List of files: " +
              listFiles().joinToString(", ").ifEmpty { "no files found" }
        )
      }
    }

  private fun createDirIfItDoesntExist() {
    if (Files.notExists(Path(DIRECTORY))) {
      logger.debug("PersistenceServiceImpl: creating directory $DIRECTORY")
      Files.createDirectory(Path(DIRECTORY))
    }
  }

  companion object {
    private const val DIRECTORY = "./persistence/"
    private const val QUERY_SEPERATOR = "\n\n"
  }

}
