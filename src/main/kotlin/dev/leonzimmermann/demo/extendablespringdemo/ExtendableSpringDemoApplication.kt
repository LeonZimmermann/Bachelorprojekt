package dev.leonzimmermann.demo.extendablespringdemo

import dev.leonzimmermann.demo.extendablespringdemo.models.Address
import dev.leonzimmermann.demo.extendablespringdemo.repositories.AddressRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication
@AutoConfiguration
class ExtendableSpringDemoApplication

fun main(args: Array<String>) {
	runApplication<ExtendableSpringDemoApplication>(*args)
}
