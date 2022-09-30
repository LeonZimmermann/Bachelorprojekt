package dev.leonzimmermann.demo.extendablespringdemo.models.fixedDatabase

import javax.persistence.*

@Entity
@Table(name = "Person")
data class Person(
  @Id @GeneratedValue var id: Long,
  @Column(name = "firstname", nullable = false) var firstname: String,
  @Column(name = "lastname", nullable = false) var lastname: String,
  @OneToOne(optional = false) @MapsId var address: Address
)