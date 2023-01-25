package dev.leonzimmermann.bachelorprojekt.services.database.impl

import dev.leonzimmermann.bachelorprojekt.generation.DatabaseSchemeService
import dev.leonzimmermann.bachelorprojekt.isBoolean
import dev.leonzimmermann.bachelorprojekt.isFloat
import dev.leonzimmermann.bachelorprojekt.isInt
import dev.leonzimmermann.bachelorprojekt.isString
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.*
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.FloatValueGenerator
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.IntValueGenerator
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.ObjectIdGenerator
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.ValueGeneratorFromStringList
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.*
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntProperty
import org.apache.jena.ontology.OntResource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
internal class DatabaseSchemeServiceImpl : DatabaseSchemeService {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Constructs the database scheme from an OntModel. The OntModel contains owl:DatatypeProperties, which
     * can be thought of as basic properties of tables, and owl:ObjectProperties, which can be thought of
     * as foreign keys. The rdfs:domain Property stores the table, which contains the property.
     */
    override fun createDatabaseSchemeFromOntology(model: OntModel): DatabaseScheme {
        val datatypeProperties =
            splitPropertiesByDomains(model, model.listDatatypeProperties().toList())
        val objectProperties =
            splitPropertiesByDomains(model, model.listObjectProperties().toList())
        val tables = (datatypeProperties + objectProperties).apply { logProperties(this) }
            .groupBy({ it.first }, { it.second })
            .apply { logger.debug("Domains: ${this.keys.joinToString(", ")}") }
            .map { mapDataToTableScheme(model, it) }
        return DatabaseScheme(tables.toTypedArray())
    }

    /**
     * A property can contain multiple domains, when multiple tables have a property with the same name. This method
     * makes sure that the property will be added to each table, and not just one.
     */
    private fun splitPropertiesByDomains(
        model: OntModel,
        properties: List<OntProperty>
    ): List<Pair<OntResource, OntProperty>> {
        val listAfterSplitting = mutableListOf<Pair<OntResource, OntProperty>>()
        properties.forEach {
            it.listDomain().toList().forEach { domain ->
                listAfterSplitting += Pair(model.getOntClass(domain.uri), it)
            }
        }
        return listAfterSplitting.toList()
    }

    private fun mapDataToTableScheme(
        ontModel: OntModel,
        entry: Map.Entry<OntResource, List<OntProperty>>
    ): TableScheme {
        val foreignKeys = entry.value.filter { it.isObjectProperty }
            .map {
                ForeignKeyScheme(
                    it.getLabel("EN"),
                    it.range.getLabel("EN") ?: it.range.localName,
                    TABLE_PRIMARY_KEY_IDENTIFIER
                )
            }
            .toTypedArray()
        return TableScheme(
            entry.key.getLabel("EN"),
            PropertyScheme(TABLE_PRIMARY_KEY_IDENTIFIER, ObjectIdGenerator()),
            foreignKeys,
            entry.value.filter { !it.isObjectProperty }.map {
                PropertyScheme(
                    it.getLabel("EN"),
                    mapOntPropertyToPropertyValueGenerator(ontModel, it)
                )
            }.toTypedArray()
        )
    }

    private fun mapOntPropertyToPropertyValueGenerator(
        ontModel: OntModel,
        property: OntProperty
    ): PropertyValueGenerator =
        when {
            isInt(property) -> IntValueGenerator(IntRange(0, 9999999))
            isFloat(property) -> FloatValueGenerator(IntRange(0, 9999999))
            isBoolean(property) -> BooleanValueGenerator()
            isString(property) -> ValueGeneratorFromStringList(
                *getPossibleStringValuesForProperty(
                    ontModel,
                    property
                )
            )
            else -> throw IllegalArgumentException("Invalid datatype: ${property.range.localName}")
        }

    private fun getPossibleStringValuesForProperty(
        ontModel: OntModel,
        property: OntProperty
    ): Array<String> {
        val instanceNames = ontModel.getOntClass(property.domain.uri).listInstances().toList()
            .map { it.getLabel("EN") }.toTypedArray()
        return if (instanceNames.isNotEmpty()) {
            instanceNames
        } else {
            createListOfRandomFallbackStrings()
        }
    }

    // TODO Adjust depending on domain
    private fun createListOfRandomFallbackStrings() =
        (0 until NUMBER_OF_RANDOM_FALLBACK_STRINGS).map {
            (0 until LENGTH_OF_RANDOM_FALLBACK_STRINGS).map {
                (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
            }.joinToString("")
        }.toTypedArray()

    private fun logProperties(list: List<Pair<OntResource, OntProperty>>) {
        logger.debug("Properties:\n${
            list.joinToString("\n") { element ->
                "Domain: ${element.first}, Name: ${element.second.getLabel("EN")}, Range: ${element.second.range}"
            }
        }")
    }

    companion object {
        private const val TABLE_PRIMARY_KEY_IDENTIFIER = "objectId"
        private const val NUMBER_OF_RANDOM_FALLBACK_STRINGS = 10
        private const val LENGTH_OF_RANDOM_FALLBACK_STRINGS = 10
    }
}
