package com.eygraber.detekt.rules.no.whitespace.after.keyword

import com.eygraber.detekt.rules.NoWhitespaceAfterKeyword
import com.eygraber.detekt.rules.assertFormat
import io.gitlab.arturbosch.detekt.api.SourceLocation
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterElseIfAutoCorrectTest {
  private val rule = NoWhitespaceAfterKeyword(
    TestConfig("autoCorrect" to true)
  )

  @Test
  fun `whitespace between an else-if keyword and the opening paren should fail with autocorrect`() {
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
  fun `more than one whitespace between an else-if keyword and the opening paren should fail with autocorrect`() {
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
  fun `whitespace between an else-if keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {} else if (false) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {} else if(false) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `more than one whitespace between an else-if keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {} else if   (false) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {} else if(false) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no whitespace between an else-if keyword and the opening paren should not fail with autocorrect`() {
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
  fun `whitespace between an if keyword and else-if keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if (true) {} else if (false) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 2, column = 3), SourceLocation(line = 2, column = 20))
  }

  @Test
  fun `more than one whitespace between an if keyword and else-if keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if   (true) {} else if   (false) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 2, column = 3), SourceLocation(line = 2, column = 20))
  }

  @Test
  fun `whitespace between an if keyword and else-if keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if (true) {} else if (false) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {} else if(false) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `more than one whitespace between an if keyword and else-if keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if   (true) {} else if   (false) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {} else if(false) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `whitespace between an else-if-else keyword and the opening paren should fail with autocorrect`() {
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
  fun `more than one whitespace between an else-if-else keyword and the opening paren should fail with autocorrect`() {
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
  fun `whitespace between an else-if-else keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {} else if (false) {} else {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {} else if(false) {} else {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `more than one whitespace between an else-if-else keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {} else if   (false) {} else {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {} else if(false) {} else {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no whitespace between an else-if-else keyword and the opening paren should not fail with autocorrect`() {
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
  fun `whitespace between an if keyword and else-if-else keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if (true) {} else if (false) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 2, column = 3), SourceLocation(line = 2, column = 20))
  }

  @Test
  fun `more than one whitespace between an if keyword and else-if-else keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if   (true) {} else if   (false) {} else {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 2, column = 3), SourceLocation(line = 2, column = 20))
  }

  @Test
  fun `whitespace between an if keyword and else-if-else keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if (true) {} else if (false) {} else {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {} else if(false) {} else {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `more than one whitespace between an if keyword and else-if-else keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if   (true) {} else if   (false) {} else {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {} else if(false) {} else {}
      |}
      """.trimMargin()
    )
  }
}
