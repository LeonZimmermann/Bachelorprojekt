package dev.leonzimmermann.demo.extendablespringdemo.services.ontology

import org.apache.jena.ontology.OntModel

interface OntologyService {
  fun createOntology(): OntModel
}