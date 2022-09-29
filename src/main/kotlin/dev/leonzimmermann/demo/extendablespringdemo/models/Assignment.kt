package dev.leonzimmermann.demo.extendablespringdemo.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "assignment")
data class Assignment(
  @Id @GeneratedValue val objectId: Long = 0,
  @Column(name = "stem", nullable = false) val stem: String,
  @Column(name ="solution", nullable = false) val solution: String
)