package dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyValueGenerator
import java.io.IOException

class PropertyValueGeneratorAdapter : TypeAdapter<PropertyValueGenerator>() {
    override fun write(outputWriter: JsonWriter, value: PropertyValueGenerator) {
        outputWriter.beginObject()
        outputWriter.name("generator")
        outputWriter.value(value.javaClass.canonicalName)
        writeGeneratorSpecificData(value, outputWriter)
        outputWriter.endObject()
    }

    private fun writeGeneratorSpecificData(
        value: PropertyValueGenerator,
        outputWriter: JsonWriter
    ) {
        when (value) {
            is ObjectIdGenerator -> writeObjectIdCounter(outputWriter, value.nextObjectId)
            is FloatValueGenerator -> writeRange(outputWriter, value.range)
            is IntValueGenerator -> writeRange(outputWriter, value.range)
            is ValueGeneratorFromStringList -> writeStringArray(outputWriter, value.values)
        }
    }

    private fun writeObjectIdCounter(outputWriter: JsonWriter, nextObjectId: Long) {
        outputWriter.name("nextObjectId")
        outputWriter.value(nextObjectId)
    }

    private fun writeRange(outputWriter: JsonWriter, range: IntRange) {
        outputWriter.name("from")
        outputWriter.value(range.first)
        outputWriter.name("to")
        outputWriter.value(range.last)
    }

    private fun writeStringArray(outputWriter: JsonWriter, array: Array<out String>) {
        outputWriter.name("strings")
        outputWriter.beginArray()
        array.forEach { outputWriter.value(it) }
        outputWriter.endArray()
    }

    override fun read(inputReader: JsonReader): PropertyValueGenerator {
        inputReader.beginObject()
        var className = ""
        var nextObjectId = 0L
        var from = 0
        var to = 0
        var strings: Array<String> = emptyArray()
        while (inputReader.hasNext()) {
            when (inputReader.nextName()) {
                "generator" -> className = inputReader.nextString()
                "nextObjectId" -> nextObjectId = inputReader.nextLong()
                "from" -> from = inputReader.nextInt()
                "to" -> to = inputReader.nextInt()
                "strings" -> strings = readStringArray(inputReader)
            }
        }
        val result = createInstanceFromClassName(className, nextObjectId, from, to, strings)
        inputReader.endObject()
        return result
    }

    private fun readStringArray(inputReader: JsonReader): Array<String> {
        inputReader.beginArray()
        val list = mutableListOf<String>()
        while (inputReader.hasNext()) {
            list += inputReader.nextString()
        }
        val result = list.toTypedArray()
        inputReader.endArray()
        return result
    }

    private fun createInstanceFromClassName(
        className: String,
        nextObjectId: Long,
        from: Int,
        to: Int,
        strings: Array<String>
    ) = when (className) {
        ObjectIdGenerator::class.java.canonicalName -> ObjectIdGenerator(nextObjectId)
        FloatValueGenerator::class.java.canonicalName -> FloatValueGenerator(IntRange(from, to))
        IntValueGenerator::class.java.canonicalName -> IntValueGenerator(IntRange(from, to))
        ValueGeneratorFromStringList::class.java.canonicalName -> ValueGeneratorFromStringList(*strings)
        BooleanValueGenerator::class.java.canonicalName -> BooleanValueGenerator()
        else -> throw IOException()
    }

}
