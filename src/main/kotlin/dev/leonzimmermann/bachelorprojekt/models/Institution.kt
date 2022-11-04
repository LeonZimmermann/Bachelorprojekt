package dev.leonzimmermann.bachelorprojekt.models

import javax.persistence.*

@Entity
@Table(name = "institution")
data class Institution(
  @Id @GeneratedValue var objectId: Long = 0,
  @Column(name = "name", nullable = false) val name: String,
  @Column(name = "type", nullable = false) val type: String,
  @OneToOne(optional = false) @MapsId val address: Address,
)