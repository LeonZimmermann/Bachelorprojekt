package dev.leonzimmermann.demo.extendablespringdemo.repositories

import dev.leonzimmermann.demo.extendablespringdemo.models.Assignment
import org.springframework.data.jpa.repository.JpaRepository

interface AssignmentRepository : JpaRepository<Assignment, Long>