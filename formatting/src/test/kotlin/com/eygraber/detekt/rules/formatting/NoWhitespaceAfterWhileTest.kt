package com.eygraber.detekt.rules.formatting

import com.eygraber.detekt.rules.common.test.hasStartSourceLocation
import dev.detekt.test.assertj.assertThat
import dev.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterWhileTest {
  private val rule = NoWhitespaceAfterKeyword()

  @Test
  fun `whitespace between a while keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  while (true) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a while keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  while   (true) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between a while keyword and the opening paren should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  while(true) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }
}
