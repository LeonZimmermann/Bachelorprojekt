package dev.leonzimmermann.bachelorprojekt.usecases.assignment

import org.apache.jena.ontology.OntModel

interface OntologyService {
  fun createEROntology(): OntModel
}