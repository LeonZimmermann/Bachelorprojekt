package dev.leonzimmermann.demo.extendablespringdemo.services.query.impl

import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class QueryServiceUnitTest {

  @Autowired
  private lateinit var queryServiceImpl: QueryServiceImpl

  @Test
  @Ignore
  fun testQuery() {
    TODO("Implement")
  }
}