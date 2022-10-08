package dev.leonzimmermann.demo.extendablespringdemo.models

import javax.persistence.*

@Entity
@Table(name = "person")
data class Person(
  @Id @GeneratedValue var objectId: Long = 0,
  @Column(name = "firstname", nullable = false) val firstname: String,
  @Column(name = "lastname", nullable = false) val lastname: String,
  @OneToOne(optional = false) @MapsId val address: Address
)