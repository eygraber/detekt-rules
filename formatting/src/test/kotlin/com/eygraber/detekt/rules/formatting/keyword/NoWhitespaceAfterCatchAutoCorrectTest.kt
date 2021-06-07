package com.eygraber.detekt.rules.formatting.keyword

import com.eygraber.detekt.rules.formatting.NoWhitespaceAfterKeyword
import com.eygraber.detekt.rules.assertFormat
import io.gitlab.arturbosch.detekt.api.SourceLocation
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterCatchAutoCorrectTest {
  private val rule = NoWhitespaceAfterKeyword(
    TestConfig("autoCorrect" to true)
  )

  @Test
  fun `whitespace between a catch keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {} catch (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a catch keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {} catch   (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
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
      """.trimMargin()
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
      """.trimMargin()
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
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a multiline catch keyword and the opening paren should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 2, column = 3)
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
      """.trimMargin()
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
      """.trimMargin()
    )
  }

  @Test
  fun `whitespace between the first catch keyword and the opening parens should fail but no whitespace between other catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch (ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `more than one whitespace between the first catch keyword and the opening parens should fail but no whitespace between other catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch   (ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `whitespace between the first catch keyword and the opening parens should format but no whitespace between other catch keywords and their opening parens should not fail with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch (ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `more than one whitespace between the first catch keyword and the opening parens should format but no whitespace between other catch keywords and their opening parens should not fail with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch   (ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `whitespace between the first multiline catch keyword and the opening parens should fail but no whitespace between other multiline catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch (ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `more than one whitespace between the first multiline catch keyword and the opening parens should fail but no whitespace between other multiline catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch   (ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `whitespace between the first multiline catch keyword and the opening parens should format but no whitespace between other multiline catch keywords and their opening parens should not fail with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch (ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `more than one whitespace between the first multiline catch keyword and the opening parens should format but no whitespace between other multiline catch keywords and their opening parens should not fail with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch   (ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no whitespace between the first catch keyword and the opening parens should not fail but whitespace between other catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `no whitespace between the first catch keyword and the opening parens should not fail but more than one whitespace between other catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch   (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `no whitespace between the first catch keyword and the opening parens should not fail but whitespace between other catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no whitespace between the first catch keyword and the opening parens should not fail but more than one whitespace between other catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but whitespace between other multiline catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but more than one whitespace between other multiline catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 3)
  }

  @Test
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but whitespace between other multiline catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but more than one whitespace between other multiline catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `whitespace between multiple catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch (ise: IllegalStateException) {} catch (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 4, column = 3), SourceLocation(line = 4, column = 3))
  }

  @Test
  fun `more than one whitespace between multiple catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch   (ise: IllegalStateException) {} catch   (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 4, column = 3), SourceLocation(line = 4, column = 3))
  }

  @Test
  fun `whitespace between multiple catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch (ise: IllegalStateException) {} catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `more than one whitespace between multiple catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch   (ise: IllegalStateException) {} catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `whitespace between multiple multiline catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch (ise: IllegalStateException) {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 4, column = 3), SourceLocation(line = 4, column = 3))
  }

  @Test
  fun `more than one whitespace between multiline multiple catch keywords and their opening parens should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch   (ise: IllegalStateException) {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 4, column = 3), SourceLocation(line = 4, column = 3))
  }

  @Test
  fun `whitespace between multiple multiline catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch (ise: IllegalStateException) {}
      |  catch (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `more than one whitespace between multiple multiline catch keywords and their opening parens should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch   (ise: IllegalStateException) {}
      |  catch   (e: Exception) {}
      |}
      """.trimMargin(),

      expected = """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no whitespace between a catch keyword and the opening paren should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {} catch(e: Exception) {}
      |}
      """.trimMargin()
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
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no whitespace between multiple catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {} catch(ise: IllegalStateException) {} catch(e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no whitespace between multiple multiline catch keywords and their opening parens should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |import java.lang.IllegalStateException
      |
      |fun foo() {
      |  try {}
      |  catch(ise: IllegalStateException) {}
      |  catch(e: Exception) {}
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }
}
