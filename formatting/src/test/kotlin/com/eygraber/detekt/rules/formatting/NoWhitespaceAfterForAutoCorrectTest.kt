package com.eygraber.detekt.rules.formatting

import com.eygraber.detekt.rules.common.test.assertFormat
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterForAutoCorrectTest {
  private val rule = NoWhitespaceAfterKeyword(
    TestConfig("autoCorrect" to true),
  )

  @Test
  fun `whitespace between a for keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  val bars = arrayOf("")
      |  for (bar in bars) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 3, column = 3)
  }

  @Test
  fun `more than one whitespace between a for keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  val bars = arrayOf("")
      |  for   (bar in bars) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 3, column = 3)
  }

  @Test
  fun `whitespace between a for keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  val bars = arrayOf("")
      |  for (bar in bars) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  val bars = arrayOf("")
      |  for(bar in bars) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `more than one whitespace between a for keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  val bars = arrayOf("")
      |  for   (bar in bars) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  val bars = arrayOf("")
      |  for(bar in bars) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no whitespace between a for keyword and the opening paren should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  val bars = arrayOf("")
      |  for(bar in bars) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }
}
