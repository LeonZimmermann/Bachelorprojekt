package dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.Datatype
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyValueGenerator
import java.io.IOException

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
        inputReader.beginObject()
        var clazz: Class<*>? = null
        var datatype: Datatype

        var nextObjectId: Long? = null
        var from: Double? = null
        var to: Double? = null
        var strings: Array<String>? = null
        while (inputReader.hasNext()) {
            when (inputReader.nextName()) {
                "generator" -> clazz = Class.forName(inputReader.nextString())
                "datatype" -> {
                    val datatypeOrdinal = inputReader.nextInt()
                    if (datatypeOrdinal >= Datatype.values().size) {
                        throw IOException("datatype of PropertyValueGenerator invalid: $datatypeOrdinal")
                    }
                    datatype = Datatype.values()[datatypeOrdinal]
                }
                "nextObjectId" -> nextObjectId = inputReader.nextLong()
                "from" -> from = inputReader.nextDouble()
                "to" -> to = inputReader.nextDouble()
                "strings" -> {
                    inputReader.beginArray()
                    val list = mutableListOf<String>()
                    while (inputReader.hasNext()) {
                        list += inputReader.nextString()
                    }
                    strings = list.toTypedArray()
                    inputReader.endArray()
                }
            }
        }
        clazz?.getDeclaredConstructor()?.newInstance() ?: throw IOException("no generator class was found")
        inputReader.endObject()
        return ObjectIdGenerator()
    }

}
