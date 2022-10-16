package dev.leonzimmermann.demo.extendablespringdemo.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import dev.leonzimmermann.demo.extendablespringdemo.services.query.QueryService
import org.hibernate.QueryException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.GsonJsonParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.GsonBuilderUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/query")
class QueryController(
  @Autowired private val queryService: QueryService
) {

  private val logger = LoggerFactory.getLogger(javaClass.name)

  @PostMapping
  fun executeQuery(@RequestBody query: String): ResponseEntity<Any> {
    return try {
      val result = queryService.executeQuery(query)
      return ResponseEntity(ObjectMapper().writeValueAsString(result), HttpStatus.OK)
    } catch (e: QueryException) {
      logger.error(e.stackTraceToString())
      ResponseEntity("Invalid query", HttpStatus.BAD_REQUEST)
    } catch (e: Exception) {
      logger.error(e.stackTraceToString())
      ResponseEntity(e.localizedMessage, HttpStatus.BAD_REQUEST)
    }
  }
}