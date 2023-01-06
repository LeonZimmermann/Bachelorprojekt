package dev.leonzimmermann.bachelorprojekt.services.persistence

import dev.leonzimmermann.bachelorprojekt.assignment.GenerationDataReader
import dev.leonzimmermann.bachelorprojekt.generation.GenerationDataWriter
import dev.leonzimmermann.bachelorprojekt.services.database.scheme.DatabaseScheme
import org.apache.jena.ontology.OntModel

interface PersistenceService: GenerationDataWriter, GenerationDataReader {}
