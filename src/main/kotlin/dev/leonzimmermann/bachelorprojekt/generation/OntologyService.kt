package dev.leonzimmermann.bachelorprojekt.generation

import org.apache.jena.ontology.OntModel

interface OntologyService {
  fun createEROntology(uri: String): OntModel
}