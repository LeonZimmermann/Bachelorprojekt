package dev.leonzimmermann.bachelorprojekt.repositories

import dev.leonzimmermann.bachelorprojekt.models.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Long>