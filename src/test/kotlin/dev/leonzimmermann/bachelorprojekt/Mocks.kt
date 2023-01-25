package dev.leonzimmermann.bachelorprojekt

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.ForeignKeyScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.PropertyScheme
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.TableScheme
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.IntValueGenerator
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.ObjectIdGenerator
import dev.leonzimmermann.bachelorprojekt.services.database.impl.valueGenerators.ValueGeneratorFromStringList
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme

fun getDatabaseScheme(): DatabaseScheme = DatabaseScheme(arrayOf(
    getPersonTableScheme("objectId"),
    getAdressTableScheme()
))

fun getAdressTableScheme(): TableScheme {
    return TableScheme(
        "Address", PropertyScheme("objectId", ObjectIdGenerator()), emptyArray(),
        getAddressPropertySchemes()
    )
}

fun getAddressPropertySchemes(): Array<PropertyScheme> {
    return arrayOf(
        PropertyScheme("street", ValueGeneratorFromStringList("Steeler Str.", "Altenessener Str.")),
        PropertyScheme("streetNumber", IntValueGenerator(IntRange(1, 10))),
        PropertyScheme("city", ValueGeneratorFromStringList("Essen", "Duesseldorf")),
        PropertyScheme(
            "state",
            ValueGeneratorFromStringList("Nordrhein-Westfalen", "Berlin", "Brandenburg")
        ),
        PropertyScheme(
            "country",
            ValueGeneratorFromStringList("Deutschland", "Oesterreich", "Schweiz")
        )
    )
}

fun getPersonTableScheme(primaryKeyIdentifier: String): TableScheme {
    return TableScheme(
        name = "Person", PropertyScheme(primaryKeyIdentifier, ObjectIdGenerator()),
        arrayOf(ForeignKeyScheme("address", "Address", primaryKeyIdentifier)),
        arrayOf(
            PropertyScheme(name = "firstname", ValueGeneratorFromStringList("Leon", "Paul", "Lukas", "Max")),
            PropertyScheme(name = "lastname", ValueGeneratorFromStringList("Zimmermann", "Mustermann", "Schreiner", "Mueller")),
        )
    )
}
