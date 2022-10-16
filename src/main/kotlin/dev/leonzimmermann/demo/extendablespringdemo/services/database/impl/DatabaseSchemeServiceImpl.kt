package dev.leonzimmermann.demo.extendablespringdemo.services.database.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.DatabaseSchemeService
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.DatabaseScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.PropertyScheme
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.TableScheme
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntProperty
import org.apache.jena.ontology.OntResource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DatabaseSchemeServiceImpl : DatabaseSchemeService {

  private val logger = LoggerFactory.getLogger(javaClass)

  /**
   * Constructs the database scheme from an OntModel. The OntModel contains owl:DatatypeProperties, which
   * can be thought of as basic properties of tables, and owl:ObjectProperties, which can be thought of
   * as foreign keys. The rdfs:domain Property stores the table, which contains the property.
   */
  override fun createDatabaseSchemeFromOntology(model: OntModel): DatabaseScheme {
    val datatypeProperties = model.listDatatypeProperties().toList()
    val objectProperties = model.listObjectProperties().toList()
    val tables =
      splitPropertiesByDomains(datatypeProperties + objectProperties).apply { logProperties(this) }
        .groupBy({ it.first }, { Pair(it.second, it.third) })
        .apply { logger.debug("Domains: ${this.keys.joinToString(", ")}") }
        .map(this::mapDataToTableScheme)
    return DatabaseScheme(tables.toTypedArray())
  }

  /**
   * A property can contain multiple domains, when multiple tables have a property with the same name. This method
   * makes sure that the property will be added to each table, and not just one.
   */
  private fun splitPropertiesByDomains(properties: List<OntProperty>): List<Triple<OntResource, String, OntResource>> {
    val listAfterSplitting = mutableListOf<Triple<OntResource, String, OntResource>>()
    properties.forEach {
      val domains = it.listDomain().toList()
      domains.forEach { domain ->
        listAfterSplitting += Triple(domain, it.localName, it.range)
      }
    }
    return listAfterSplitting.toList()
  }

  private fun mapDataToTableScheme(entry: Map.Entry<OntResource, List<Pair<String, OntResource>>>): TableScheme {
    return TableScheme(
      entry.key.localName,
      PropertyScheme("objectId", "Long"),
      entry.value.map { pair -> PropertyScheme(pair.first, pair.second.localName) }.toTypedArray()
    )
  }

  private fun logProperties(list: List<Triple<OntResource, String, OntResource>>) {
    logger.debug("Properties:\n${
      list.joinToString("\n") { element ->
        "Domain: ${element.first}, Name: ${element.second}, Range: ${element.third.localName}"
      }
    }")
  }
}