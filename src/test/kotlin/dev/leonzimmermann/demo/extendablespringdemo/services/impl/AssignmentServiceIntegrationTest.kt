package dev.leonzimmermann.demo.extendablespringdemo.services.impl

import dev.leonzimmermann.demo.extendablespringdemo.services.assignment.impl.AssignmentServiceImpl
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class AssignmentServiceIntegrationTest {

  @Autowired
  private lateinit var assignmentService: AssignmentServiceImpl

  @Test
  fun `when generateNewAssignment() is called, then the method returns a valid assignment`() {
    val assignment = assignmentService.generateNewAssignment()
    assertNotNull(assignment.stem)
    assertNotNull(assignment.solution)
  }
}