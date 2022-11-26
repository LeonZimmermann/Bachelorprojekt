package dev.leonzimmermann.bachelorprojekt.services.ontology.impl

import dev.leonzimmermann.bachelorprojekt.assignment.OntologyService
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.springframework.stereotype.Service

@Service
class OntologyServiceImpl: OntologyService {
  /**
   * The result has to be a normalized Ontology, i.e. an ontology that only has entities and relationships
   * that correspond to ER-Diagrams.
   */
  override fun createEROntology(): OntModel {
    val loadedBaseModel: Model = RDFDataMgr.loadModel("customontology.ttl")
    val ontModelSpec = OntModelSpec(OntModelSpec.OWL_MEM)
    return ModelFactory.createOntologyModel(ontModelSpec, loadedBaseModel)
  }
}