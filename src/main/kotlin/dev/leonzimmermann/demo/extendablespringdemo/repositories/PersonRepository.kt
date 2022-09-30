package dev.leonzimmermann.demo.extendablespringdemo.repositories

import dev.leonzimmermann.demo.extendablespringdemo.models.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Long>