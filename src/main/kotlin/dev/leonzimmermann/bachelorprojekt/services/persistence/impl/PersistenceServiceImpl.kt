package dev.leonzimmermann.bachelorprojekt.services.persistence.impl

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
import kotlin.streams.toList

@Service
class PersistenceServiceImpl : PersistenceService {

  private val logger: Logger = LoggerFactory.getLogger(javaClass)

  override suspend fun saveOntologyToDisk(fileName: String, ontology: OntModel): Boolean =
    withContext(Dispatchers.IO) {
      logger.debug("Executing saveOntologyToDisk")
      createDirIfItDoesntExist()
      val file = File("$DIRECTORY$fileName$ONTOLOGY_EXT")
      throwExceptionIfFileAlreadyExists(file, fileName)
      try {
        if (file.createNewFile()) {
          file.outputStream().use {
            ontology.write(it, "TURTLE")
          }
          logger.debug("Saved ${file.path}")
        }
        true
      } catch (e: Exception) {
        e.printStackTrace()
        logger.error("Could not save Ontology ${file.path}. Removing file...")
        removeFile(file.name)
        false
      }
    }

  override suspend fun saveDatabaseSchemeToDisk(
    fileName: String,
    databaseScheme: DatabaseScheme
  ): Boolean =
    withContext(Dispatchers.IO) {
      logger.debug("Executing saveDatabaseSchemeToDisk")
      createDirIfItDoesntExist()
      val file = File("$DIRECTORY$fileName$DATABASE_SCHEME_EXT")
      throwExceptionIfFileAlreadyExists(file, fileName)
      try {
        if (file.createNewFile()) {
          file.writeText(databaseScheme.toJson())
          logger.debug("PersistenceServiceImpl: saved ${file.path}")
          true
        } else false
      } catch (e: Exception) {
        e.printStackTrace()
        logger.error("PersistenceServiceImpl: could not save DatabaseScheme ${file.path}. Removing file...")
        removeFile(file.name)
        false
      }
    }

  override suspend fun saveSQLToDisk(fileName: String, queries: Array<String>): Boolean =
    withContext(Dispatchers.IO) {
      logger.debug("Executing saveSQLToDisk")
      createDirIfItDoesntExist()
      val file = File("$DIRECTORY$fileName$SQL_EXT")
      throwExceptionIfFileAlreadyExists(file, fileName)
      try {
        if (file.createNewFile()) {
          file.writeText(queries.joinToString(QUERY_SEPERATOR))
          logger.debug("PersistenceServiceImpl: saved ${file.path}")
        }
        true
      } catch (e: Exception) {
        e.printStackTrace()
        logger.error("Could not save SQL-Queries ${file.path}. Removing file...")
        removeFile(file.name)
        false
      }
    }

  override suspend fun loadOntologyFromDisk(fileName: String): OntModel =
    withContext(Dispatchers.IO) {
      logger.debug("Executing loadOntologyFromDisk")
      val file = File("$DIRECTORY$fileName$ONTOLOGY_EXT")
      throwExceptionIfFileDoesntExist(file, fileName)
      logger.debug("Loading $fileName")
      ModelFactory.createOntologyModel(
        OntModelSpec(OntModelSpec.OWL_MEM),
        RDFDataMgr.loadModel(file.absolutePath)
      )
    }

  override suspend fun loadDatabaseSchemeFromDisk(fileName: String): DatabaseScheme =
    withContext(Dispatchers.IO) {
      logger.debug("Executing loadDatabaseSchemeFromDisk")
      val file = File("$DIRECTORY$fileName$DATABASE_SCHEME_EXT")
      throwExceptionIfFileDoesntExist(file, fileName)
      logger.debug("Loading $fileName")
      DatabaseScheme.fromJson(file.readText())
    }

  override suspend fun loadSQLFromDisk(fileName: String): Array<String> =
    withContext(Dispatchers.IO) {
      logger.debug("Executing loadSQLFromDisk")
      val file = File("$DIRECTORY$fileName$SQL_EXT")
      throwExceptionIfFileDoesntExist(file, fileName)
      logger.debug("Loading $fileName")
      file.readLines().joinToString("\n").split(QUERY_SEPERATOR).toTypedArray()
    }

  override suspend fun removeFile(fileName: String) = withContext(Dispatchers.IO) {
    val file = File("$DIRECTORY$fileName")
    throwExceptionIfFileDoesntExist(file, fileName)
    logger.debug("Removing file \"$fileName\"")
    file.delete()
  }

  override suspend fun listFiles(): Array<String> = withContext(Dispatchers.IO) {
    createDirIfItDoesntExist()
    Files.list(Path(DIRECTORY))
      .map { it.fileName.name }
      .toList()
      .toTypedArray()
  }

  private suspend fun throwExceptionIfFileAlreadyExists(file: File, fileName: String) =
    withContext(Dispatchers.IO) {
      if (file.exists()) {
        throw IllegalArgumentException(
          "File $fileName already exists! Array of files: " +
              listFiles().joinToString(", ").ifEmpty { "no files found" }
        )
      }
    }

  private suspend fun throwExceptionIfFileDoesntExist(file: File, fileName: String) =
    withContext(Dispatchers.IO) {
      if (!file.exists()) {
        throw IllegalArgumentException(
          "File $fileName was not found! Array of files: " +
              listFiles().joinToString(", ").ifEmpty { "no files found" }
        )
      }
    }

  private fun createDirIfItDoesntExist() {
    if (Files.notExists(Path(DIRECTORY))) {
      logger.debug("Creating directory $DIRECTORY")
      Files.createDirectory(Path(DIRECTORY))
    }
  }

  companion object {
    private const val DIRECTORY = "./persistence/"
    private const val ONTOLOGY_EXT = ".ttl"
    private const val DATABASE_SCHEME_EXT = ".json"
    private const val SQL_EXT = ".sql"
    private const val QUERY_SEPERATOR = "\n\n"
  }

}
