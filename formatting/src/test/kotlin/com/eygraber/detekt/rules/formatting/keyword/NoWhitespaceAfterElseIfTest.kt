package com.eygraber.detekt.rules.formatting.keyword

import com.eygraber.detekt.rules.formatting.NoWhitespaceAfterKeyword
import io.gitlab.arturbosch.detekt.api.SourceLocation
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterElseIfTest {
  private val rule = NoWhitespaceAfterKeyword()

  @Test
  fun `whitespace between an else-if keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {} else if (false) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 20)
  }

  @Test
  fun `more than one whitespace between an else-if keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {} else if   (false) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 20)
  }

  @Test
  fun `no whitespace between an else-if keyword and the opening paren should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {} else if(false) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `whitespace between an if keyword and else-if keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if (true) {} else if (false) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 2, column = 3), SourceLocation(line = 2, column = 21))
  }

  @Test
  fun `more than one whitespace between an if keyword and else-if keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if   (true) {} else if   (false) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 2, column = 3), SourceLocation(line = 2, column = 23))
  }

  @Test
  fun `whitespace between an else-if-else keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {} else if (false) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 20)
  }

  @Test
  fun `more than one whitespace between an else-if-else keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {} else if   (false) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 20)
  }

  @Test
  fun `no whitespace between an else-if-else keyword and the opening paren should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {} else if(false) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `whitespace between an if keyword and else-if-else keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if (true) {} else if (false) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 2, column = 3), SourceLocation(line = 2, column = 21))
  }

  @Test
  fun `more than one whitespace between an if keyword and else-if-else keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if   (true) {} else if   (false) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 2, column = 3), SourceLocation(line = 2, column = 23))
  }
}
