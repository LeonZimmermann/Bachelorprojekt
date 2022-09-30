package dev.leonzimmermann.demo.extendablespringdemo.models.fixedDatabase

import javax.persistence.*

@Entity
@Table(name = "Address")
data class Address(
  @Id @GeneratedValue val id: Long = 0,
  @Column(name = "street") val street: String,
  @Column(name = "city") val city: String,
  @Column(name = "state") val state: String,
  @Column(name = "postalCode") val postalCode: Int,
  @Column(name = "country") val country: String,
)