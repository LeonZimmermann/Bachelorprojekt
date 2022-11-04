package dev.leonzimmermann.bachelorprojekt.repositories

import dev.leonzimmermann.bachelorprojekt.models.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long>