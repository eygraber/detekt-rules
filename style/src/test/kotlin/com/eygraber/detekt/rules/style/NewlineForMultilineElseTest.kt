package com.eygraber.detekt.rules.style

import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NewlineForMultilineElseTest {
  private val rule = NewlineForMultilineKeyword()

  @Test
  fun `no newline before else should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  } else {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `newline before else should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before else with else if should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |  
      |  }
      |  else if(false) {
      |   
      |  } else {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 7, column = 5)
  }

  @Test
  fun `newline before else with else if should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else if(false) {
      |    
      |  }
      |  else {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `single line if else should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) continue else continue
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }
}
