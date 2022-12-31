package dev.leonzimmermann.bachelorprojekt.services.persistence.impl

import dev.leonzimmermann.bachelorprojekt.getAdressTableScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PersistenceServiceUnitTest {

  private val persistenceService = PersistenceServiceImpl()

  @Test
  fun testPersistenceForDatabaseScheme() {
    val databaseScheme = DatabaseScheme(arrayOf(getAdressTableScheme()))
    val fileName = "testPersistenceForDatabaseScheme"
    runBlocking {
      assertThat(persistenceService.saveDatabaseSchemeToDisk(fileName, databaseScheme)).isTrue
      assertThat(persistenceService.loadDatabaseSchemeFromDisk(fileName)).isEqualTo(databaseScheme)
    }
  }
}