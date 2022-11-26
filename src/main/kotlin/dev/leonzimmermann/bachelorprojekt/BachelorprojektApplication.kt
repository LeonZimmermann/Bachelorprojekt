package dev.leonzimmermann.bachelorprojekt

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@AutoConfiguration
class BachelorprojektApplication

fun main(args: Array<String>) {
	runApplication<BachelorprojektApplication>(*args)
}
