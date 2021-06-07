package com.eygraber.detekt.rules.formatting.keyword

import com.eygraber.detekt.rules.assertFormat
import com.eygraber.detekt.rules.formatting.NoWhitespaceAfterKeyword
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterWhileAutoCorrectTest {
  private val rule = NoWhitespaceAfterKeyword(
    TestConfig("autoCorrect" to true)
  )

  @Test
  fun `whitespace between a while keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  while (true) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a while keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  while   (true) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `whitespace between a while keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  while (true) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  while(true) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `more than one whitespace between a while keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  while   (true) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  while(true) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no whitespace between a while keyword and the opening paren should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  while(true) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }
}
