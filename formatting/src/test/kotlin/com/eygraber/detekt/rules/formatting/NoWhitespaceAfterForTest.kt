package com.eygraber.detekt.rules.formatting

import com.eygraber.detekt.rules.common.test.hasStartSourceLocation
import dev.detekt.test.assertj.assertThat
import dev.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterForTest {
  private val rule = NoWhitespaceAfterKeyword()

  @Test
  fun `whitespace between a for keyword and the opening paren should fail`() {
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
  fun `more than one whitespace between a for keyword and the opening paren should fail`() {
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
  fun `no whitespace between a for keyword and the opening paren should not fail`() {
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
