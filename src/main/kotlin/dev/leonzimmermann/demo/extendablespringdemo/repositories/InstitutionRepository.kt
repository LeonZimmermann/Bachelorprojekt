package dev.leonzimmermann.demo.extendablespringdemo.repositories

import dev.leonzimmermann.demo.extendablespringdemo.models.Institution
import org.springframework.data.jpa.repository.JpaRepository

interface InstitutionRepository : JpaRepository<Institution, Long>