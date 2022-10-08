package dev.leonzimmermann.demo.extendablespringdemo.models

import javax.persistence.*

@Entity
@Table(name = "address")
data class Address(
  @Id @GeneratedValue var objectId: Long = 0,
  @Column(name = "street") val street: String,
  @Column(name = "city") val city: String,
  @Column(name = "state") val state: String,
  @Column(name = "postalCode") val postalCode: Int,
  @Column(name = "country") val country: String,
)