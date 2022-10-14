package dev.leonzimmermann.demo.extendablespringdemo.services.query.impl

import org.junit.Assert.*
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class QueryServiceImplUnitTest {

  @Autowired
  private lateinit var queryServiceImpl: QueryServiceImpl

  @Test
  fun testQuery() {
    TODO("Implement")
  }
}