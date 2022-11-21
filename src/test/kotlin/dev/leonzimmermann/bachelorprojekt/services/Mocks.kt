package dev.leonzimmermann.bachelorprojekt.services

import dev.leonzimmermann.bachelorprojekt.services.database.scheme.*

fun getAdressTableScheme(): TableScheme {
    return TableScheme(
        "Address", PropertyScheme("objectId", ObjectIdGenerator()), emptyArray(), arrayOf(
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
    )
}
