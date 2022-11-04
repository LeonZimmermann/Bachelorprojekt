package dev.leonzimmermann.bachelorprojekt.repositories

import dev.leonzimmermann.bachelorprojekt.models.Institution
import org.springframework.data.jpa.repository.JpaRepository

interface InstitutionRepository : JpaRepository<Institution, Long>