package com.eygraber.detekt.rules.formatting

import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterDoWhileTest {
  private val rule = NoWhitespaceAfterKeyword()

  @Test
  fun `whitespace between a while from a do-while keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  do {} while (true) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a while from a do-while keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  do {} while   (true) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between a while from a do-while keyword and the opening paren should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  do {} while(true) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }
}
