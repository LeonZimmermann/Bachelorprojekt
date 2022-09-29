package dev.leonzimmermann.demo.extendablespringdemo.controllers

import dev.leonzimmermann.demo.extendablespringdemo.models.Person
import dev.leonzimmermann.demo.extendablespringdemo.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/person")
class PersonController {

  @Autowired
  private lateinit var personRepository: PersonRepository

  @GetMapping("/all")
  fun getAllPersons(): List<Person> {
    return personRepository.findAll()
  }

  @GetMapping("/get/{id}")
  fun getPersonById(@RequestParam("id") id: Long): Person {
    return personRepository.findById(id)
      .orElseThrow { java.lang.IllegalArgumentException("Could not find person with id $id") }
  }

  @PostMapping("/add")
  fun addPerson(@RequestBody person: Person): Person {
    return personRepository.save(person)
  }
}