package dev.leonzimmermann.bachelorprojekt.models

import javax.persistence.*

@Entity
@Table(name = "occupation")
data class Occupation(
  @Id @GeneratedValue var objectId: Long = 0,
  @Column(name = "name", nullable = false) val name: String,
  @OneToOne(optional = false) @MapsId val person: Person,
  @OneToOne(optional = false) @MapsId val institution: Institution
)