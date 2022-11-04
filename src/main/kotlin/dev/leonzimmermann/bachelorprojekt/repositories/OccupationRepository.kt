package dev.leonzimmermann.bachelorprojekt.repositories

import dev.leonzimmermann.bachelorprojekt.models.Occupation
import org.springframework.data.jpa.repository.JpaRepository

interface OccupationRepository : JpaRepository<Occupation, Long>