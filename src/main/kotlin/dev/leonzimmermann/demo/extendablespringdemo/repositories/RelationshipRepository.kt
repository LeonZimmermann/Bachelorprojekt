package dev.leonzimmermann.demo.extendablespringdemo.repositories

import dev.leonzimmermann.demo.extendablespringdemo.models.Relationship
import org.springframework.data.jpa.repository.JpaRepository

interface RelationshipRepository : JpaRepository<Relationship, Long>