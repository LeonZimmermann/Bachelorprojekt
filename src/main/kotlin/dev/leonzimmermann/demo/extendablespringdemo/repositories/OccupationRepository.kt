package dev.leonzimmermann.demo.extendablespringdemo.repositories

import dev.leonzimmermann.demo.extendablespringdemo.models.Occupation
import org.springframework.data.jpa.repository.JpaRepository

interface OccupationRepository : JpaRepository<Occupation, Long>