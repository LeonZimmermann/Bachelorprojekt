package dev.leonzimmermann.bachelorprojekt.services.database.impl

import dev.leonzimmermann.bachelorprojekt.getDatabaseScheme
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class DatabaseTableValueInserterUnitTest {

    @Test
    fun testCreateInsertStatements() {
        val databaseScheme = getDatabaseScheme()
        val insertStatements = """
            INSERT INTO Address(street, streetNumber, city, state, country)
            VALUES(0, Steeler Str., 6, Duesseldorf, Berlin, Schweiz);
            INSERT INTO Address(street, streetNumber, city, state, country)
            VALUES(1, Steeler Str., 3, Duesseldorf, Brandenburg, Schweiz);
            INSERT INTO Address(street, streetNumber, city, state, country)
            VALUES(2, Steeler Str., 5, Duesseldorf, Nordrhein-Westfalen, Oesterreich);
            INSERT INTO Address(street, streetNumber, city, state, country)
            VALUES(3, Steeler Str., 3, Duesseldorf, Berlin, Deutschland);
            INSERT INTO Address(street, streetNumber, city, state, country)
            VALUES(4, Steeler Str., 2, Essen, Nordrhein-Westfalen, Oesterreich);
            
            INSERT INTO Person(firstname, lastname, address)
            VALUES(0, Lukas, Zimmermann, 0);
            INSERT INTO Person(firstname, lastname, address)
            VALUES(1, Max, Zimmermann, 1);
            INSERT INTO Person(firstname, lastname, address)
            VALUES(2, Paul, Mustermann, 4);
            INSERT INTO Person(firstname, lastname, address)
            VALUES(3, Lukas, Mueller, 3);
            INSERT INTO Person(firstname, lastname, address)
            VALUES(4, Leon, Zimmermann, 1);
            """.trimIndent().trim()
        assertEquals(
            insertStatements,
            DatabaseTableValueInserter(Random(1000)).createInsertStatements(databaseScheme, 5)
                .joinToString("\n\n")
        )

    }
}
