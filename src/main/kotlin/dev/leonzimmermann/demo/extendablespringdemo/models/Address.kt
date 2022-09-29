package dev.leonzimmermann.demo.extendablespringdemo.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "Address")
data class Address(
  @Id @GeneratedValue val id: Int? = null,
  @Column(name = "street") val street: String? = null,
  @Column(name = "city") val city: String? = null,
  @Column(name = "state") val state: String? = null,
  @Column(name = "postalCode") val postalCode: Int? = null,
  @Column(name = "country") val country: String? = null,
)