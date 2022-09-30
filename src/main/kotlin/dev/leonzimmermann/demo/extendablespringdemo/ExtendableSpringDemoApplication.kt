package dev.leonzimmermann.demo.extendablespringdemo

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@AutoConfiguration
class ExtendableSpringDemoApplication

fun main(args: Array<String>) {
	runApplication<ExtendableSpringDemoApplication>(*args)
}
