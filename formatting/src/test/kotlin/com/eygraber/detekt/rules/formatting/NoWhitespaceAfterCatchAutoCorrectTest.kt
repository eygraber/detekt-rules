package com.eygraber.detekt.rules.formatting

import com.eygraber.detekt.rules.common.test.IMPORT_ISE
import com.eygraber.detekt.rules.common.test.assertFormat
import com.eygraber.detekt.rules.common.test.hasStartSourceLocation
import com.eygraber.detekt.rules.common.test.hasStartSourceLocations
import dev.detekt.api.SourceLocation
import dev.detekt.test.TestConfig
import dev.detekt.test.assertj.assertThat
import dev.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterCatchAutoCorrectTest {
  private val rule = NoWhitespaceAfterKeyword(
    TestConfig("autoCorrect" to true),
  )

  @Test
  fun `whitespace between a catch keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {} catch (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a catch keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {} catch   (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `whitespace between a catch keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {} catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `more than one whitespace between a catch keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {} catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `whitespace between a multiline catch keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a multiline catch keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `whitespace between a multiline catch keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `more than one whitespace between a multiline catch keyword and the opening paren should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `whitespace between the first catch keyword and the opening parens should fail but no whitespace between other catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch (ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `more than one whitespace between the first catch keyword and the opening parens should fail but no whitespace between other catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch   (ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `whitespace between the first catch keyword and the opening parens should format but no whitespace between other catch keywords and their opening parens should not fail with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch (ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `more than one whitespace between the first catch keyword and the opening parens should format but no whitespace between other catch keywords and their opening parens should not fail with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch   (ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `whitespace between the first multiline catch keyword and the opening parens should fail but no whitespace between other multiline catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch (ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `more than one whitespace between the first multiline catch keyword and the opening parens should fail but no whitespace between other multiline catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch   (ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `whitespace between the first multiline catch keyword and the opening parens should format but no whitespace between other multiline catch keywords and their opening parens should not fail with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch (ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `more than one whitespace between the first multiline catch keyword and the opening parens should format but no whitespace between other multiline catch keywords and their opening parens should not fail with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch   (ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no whitespace between the first catch keyword and the opening parens should not fail but whitespace between other catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `no whitespace between the first catch keyword and the opening parens should not fail but more than one whitespace between other catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch   (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `no whitespace between the first catch keyword and the opening parens should not fail but whitespace between other catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no whitespace between the first catch keyword and the opening parens should not fail but more than one whitespace between other catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but whitespace between other multiline catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but more than one whitespace between other multiline catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but whitespace between other multiline catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but more than one whitespace between other multiline catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `whitespace between multiple catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch (ise: IllegalStateException) {} catch (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 4, column = 3),
      SourceLocation(line = 4, column = 3),
    )
  }

  @Test
  fun `more than one whitespace between multiple catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch   (ise: IllegalStateException) {} catch   (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 4, column = 3),
      SourceLocation(line = 4, column = 3),
    )
  }

  @Test
  fun `whitespace between multiple catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch (ise: IllegalStateException) {} catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `more than one whitespace between multiple catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch   (ise: IllegalStateException) {} catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `whitespace between multiple multiline catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch (ise: IllegalStateException) {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 4, column = 3),
      SourceLocation(line = 4, column = 3),
    )
  }

  @Test
  fun `more than one whitespace between multiline multiple catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch   (ise: IllegalStateException) {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 4, column = 3),
      SourceLocation(line = 4, column = 3),
    )
  }

  @Test
  fun `whitespace between multiple multiline catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch (ise: IllegalStateException) {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `more than one whitespace between multiple multiline catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch   (ise: IllegalStateException) {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no whitespace between a catch keyword and the opening paren should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no whitespace between a multiline catch keyword and the opening paren should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no whitespace between multiple catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no whitespace between multiple multiline catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |$IMPORT_ISE
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }
}
