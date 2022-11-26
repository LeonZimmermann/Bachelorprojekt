package dev.leonzimmermann.bachelorprojekt.assignment

import org.apache.jena.ontology.OntModel

interface OntologyService {
  fun createEROntology(): OntModel
}