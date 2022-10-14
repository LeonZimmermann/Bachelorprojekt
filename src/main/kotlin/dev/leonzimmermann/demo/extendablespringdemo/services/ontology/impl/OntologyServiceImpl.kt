package dev.leonzimmermann.demo.extendablespringdemo.services.ontology.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.ontology.OntologyService
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.springframework.stereotype.Service

@Service
class OntologyServiceImpl: OntologyService {
  override fun createOntology(): OntModel {
    val loadedBaseModel: Model = RDFDataMgr.loadModel("customontology.ttl")
    val ontModelSpec = OntModelSpec(OntModelSpec.OWL_MEM)
    return ModelFactory.createOntologyModel(ontModelSpec, loadedBaseModel)
  }
}