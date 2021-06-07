package com.eygraber.detekt.rules.formatting.keyword

import com.eygraber.detekt.rules.formatting.NoWhitespaceAfterKeyword
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterIfTest {
  private val rule = NoWhitespaceAfterKeyword()

  @Test
  fun `whitespace between an if keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if (true) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between an if keyword and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if   (true) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between an if keyword and the opening paren should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `whitespace between an if keyword and the opening brace with an else should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if (true) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between an if keyword and the opening brace with an else should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if   (true) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between an if keyword and the opening brace with an else should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `whitespace between an if keyword and the opening brace with an else-if should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if (true) {} else if(false) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between an if keyword and the opening brace with an else-if should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if   (true) {} else if(false) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between an if keyword and the opening brace with an else-if should not fail`() {
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
  fun `whitespace between an if keyword and the opening brace with an else-if else should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if (true) {} else if(false) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between an if keyword and the opening brace with an else-if else should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if   (true) {} else if(false) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between an if keyword and the opening brace with an else-if else should not fail`() {
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
  fun `whitespace between a multiline if keyword should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if (
      |    true
      |  ) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a multiline if keyword should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if   (
      |    true
      |  ) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between a multiline if keyword should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(
      |    true
      |  ) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }
}
