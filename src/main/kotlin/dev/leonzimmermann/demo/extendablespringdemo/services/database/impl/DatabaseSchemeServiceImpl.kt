package dev.leonzimmermann.demo.extendablespringdemo.services.database.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.database.DatabaseSchemeService
import dev.leonzimmermann.demo.extendablespringdemo.services.database.scheme.*
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
    val datatypeProperties = splitPropertiesByDomains(model.listDatatypeProperties().toList())
    val objectProperties = splitPropertiesByDomains(model.listObjectProperties().toList())
    val tables = (datatypeProperties + objectProperties).apply { logProperties(this) }
      .groupBy({ it.first }, { it.second })
      .apply { logger.debug("Domains: ${this.keys.joinToString(", ")}") }
      .map(this::mapDataToTableScheme)
    return DatabaseScheme(tables.toTypedArray())
  }

  /**
   * A property can contain multiple domains, when multiple tables have a property with the same name. This method
   * makes sure that the property will be added to each table, and not just one.
   */
  private fun splitPropertiesByDomains(properties: List<OntProperty>): List<Pair<OntResource, OntProperty>> {
    val listAfterSplitting = mutableListOf<Pair<OntResource, OntProperty>>()
    properties.forEach {
      val domains = it.listDomain().toList()
      domains.forEach { domain ->
        listAfterSplitting += Pair(domain, it)
      }
    }
    return listAfterSplitting.toList()
  }

  private fun mapDataToTableScheme(entry: Map.Entry<OntResource, List<OntProperty>>): TableScheme {
    val foreignKeys = entry.value.filter { it.isObjectProperty }
      .map { ForeignKeyScheme(it.localName, it.range.localName, TABLE_PRIMARY_KEY_IDENTIFIER) }
      .toTypedArray()
    return TableScheme(
      entry.key.localName,
      PropertyScheme(TABLE_PRIMARY_KEY_IDENTIFIER, ObjectIdGenerator()),
      foreignKeys,
      entry.value.filter { !it.isObjectProperty }.map {
        PropertyScheme(
          it.localName,
          mapOntRangeToPropertyValueGenerator(it.range)
        )
      }.toTypedArray()
    )
  }

  // TODO get values for ValueGeneratorFromList in DatabaseSchemeServiceImpl
  private fun mapOntRangeToPropertyValueGenerator(range: OntResource): PropertyValueGenerator =
    when (getDatatypeStringForRange(range)) {
      "integer" -> IntValueGenerator(IntRange(0, 9999999))
      "string" -> ValueGeneratorFromStringList("TODO get values for ValueGeneratorFromList in DatabaseSchemeServiceImpl")
      else -> throw IllegalArgumentException("Invalid datatype: ${getDatatypeStringForRange(range)}")
    }

  private fun getDatatypeStringForRange(range: OntResource) = range.localName

  private fun logProperties(list: List<Pair<OntResource, OntProperty>>) {
    logger.debug("Properties:\n${
      list.joinToString("\n") { element ->
        "Domain: ${element.first}, Name: ${element.second.localName}, Range: ${element.second.range}"
      }
    }")
  }

  companion object {
    private const val TABLE_PRIMARY_KEY_IDENTIFIER = "objectId"
  }
}