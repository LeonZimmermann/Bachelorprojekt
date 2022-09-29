package dev.leonzimmermann.demo.extendablespringdemo.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MapsId
import javax.persistence.OneToOne

@Entity(name = "Person")
data class Person(
  @Id @GeneratedValue var id: Int,
  @Column(name = "firstname", nullable = false) var firstname: String,
  @Column(name = "lastname", nullable = false) var lastname: String,
  @OneToOne(optional = false) @MapsId var address: Address
)