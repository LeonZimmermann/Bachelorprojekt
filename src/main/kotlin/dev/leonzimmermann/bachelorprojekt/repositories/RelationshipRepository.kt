package dev.leonzimmermann.bachelorprojekt.repositories

import dev.leonzimmermann.bachelorprojekt.models.Relationship
import org.springframework.data.jpa.repository.JpaRepository

interface RelationshipRepository : JpaRepository<Relationship, Long>