package com.eygraber.detekt.rules.style

import com.eygraber.detekt.rules.common.test.assertFormat
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NewlineForMultilineElseAutoCorrectTest {
  private val rule = NewlineForMultilineKeyword(
    TestConfig("autoCorrect" to true)
  )

  @Test
  fun `no newline before else should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  } else {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before else should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {
      |   
      |  } else {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else {
      |  
      |  }
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `newline before else should not fail with autocorrect`() {
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
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }
}
