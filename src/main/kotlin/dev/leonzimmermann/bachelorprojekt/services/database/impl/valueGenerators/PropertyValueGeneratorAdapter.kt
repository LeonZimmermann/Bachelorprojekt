package dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyValueGenerator

class PropertyValueGeneratorAdapter: TypeAdapter<PropertyValueGenerator>() {
    override fun write(outputWriter: JsonWriter, value: PropertyValueGenerator) {
        outputWriter.beginObject()
        outputWriter.name("generator")
        outputWriter.value(value.javaClass.canonicalName)
        outputWriter.name("datatype")
        outputWriter.value(value.datatype.ordinal)
        when (value) {
            is ObjectIdGenerator -> {
                outputWriter.name("nextObjectId")
                outputWriter.value(value.nextObjectId)
            }
            is FloatValueGenerator -> {
                outputWriter.name("from")
                outputWriter.value(value.range.first)
                outputWriter.name("to")
                outputWriter.value(value.range.last)
            }
            is IntValueGenerator -> {
                outputWriter.name("from")
                outputWriter.value(value.range.first)
                outputWriter.name("to")
                outputWriter.value(value.range.last)
            }
            is ValueGeneratorFromStringList -> {
                outputWriter.name("strings")
                outputWriter.beginArray()
                value.values.forEach { outputWriter.value(it) }
                outputWriter.endArray()
            }
        }
        outputWriter.endObject()
    }

    override fun read(inputReader: JsonReader): PropertyValueGenerator {

    }

}
