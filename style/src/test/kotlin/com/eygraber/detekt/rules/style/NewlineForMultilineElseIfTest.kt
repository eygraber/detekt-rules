package com.eygraber.detekt.rules.style

import io.gitlab.arturbosch.detekt.api.SourceLocation
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NewlineForMultilineElseIfTest {
  private val rule = NewlineForMultilineKeyword()

  @Test
  fun `no newline before else if should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  } else if(false) {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `newline before else if should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else if(false) {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before else if with else should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |  
      |  } else if(false) {
      |   
      |  }
      |  else {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before else if and else should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |  
      |  } else if(false) {
      |   
      |  } else {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 4, column = 5), SourceLocation(line = 6, column = 5))
  }

  @Test
  fun `single line if else if should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) continue else if(false) continue
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `single line if else if else should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) continue else if(false) continue else continue
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }
}
