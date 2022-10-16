package dev.leonzimmermann.demo.extendablespringdemo.services.database.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.DatabaseService
import org.apache.jena.ontology.OntModel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class DatabaseServiceImpl : DatabaseService {

  private val logger = LoggerFactory.getLogger(javaClass)

  override fun createDatabaseFromOntology(model: OntModel) {
    model.listClasses().forEach { ontClass ->
      logger.debug("Name of class: ${ontClass.localName}")
      ontClass.listDeclaredProperties().forEach { ontProperty ->
        logger.debug("Adding property ${ontProperty.localName} to ${ontClass.localName}")
      }
    }
  }
}