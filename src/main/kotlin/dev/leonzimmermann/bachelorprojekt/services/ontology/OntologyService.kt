package dev.leonzimmermann.bachelorprojekt.services.ontology

import org.apache.jena.ontology.OntModel

interface OntologyService {
  fun createEROntology(): OntModel
}