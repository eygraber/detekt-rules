package com.eygraber.detekt.rules.formatting

import com.eygraber.detekt.rules.common.test.assertFormat
import com.eygraber.detekt.rules.common.test.hasStartSourceLocation
import dev.detekt.test.TestConfig
import dev.detekt.test.assertj.assertThat
import dev.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterDoWhileAutoCorrectTest {
  private val rule = NoWhitespaceAfterKeyword(
    TestConfig("autoCorrect" to true),
  )

  @Test
  fun `whitespace between a while from a do-while keyword and the opening paren should fail with autocorrect`() {
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
  fun `more than one whitespace between a while from a do-while keyword and the opening paren should fail with autocorrect`() {
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
  fun `whitespace between a while from a do-while keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  do {} while (true) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  do {} while(true) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `more than one whitespace between a while from a do-while keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  do {} while   (true) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  do {} while(true) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no whitespace between a while from a do-while keyword and the opening paren should not fail with autocorrect`() {
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
