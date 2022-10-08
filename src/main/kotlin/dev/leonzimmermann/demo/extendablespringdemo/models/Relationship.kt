package dev.leonzimmermann.demo.extendablespringdemo.models

import javax.persistence.*

@Entity
@Table(name = "relationship")
data class Relationship(
  @Id @GeneratedValue var objectId: Long = 0,
  @OneToOne(optional = false) @MapsId val person: Person,
  @OneToOne(optional = false) @MapsId val otherPerson: Person
)