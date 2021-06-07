package com.eygraber.detekt.rules.no.whitespace.after.keyword

import com.eygraber.detekt.rules.NoWhitespaceAfterKeyword
import io.gitlab.arturbosch.detekt.api.SourceLocation
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterCatchTest {
  private val rule = NoWhitespaceAfterKeyword()

  @Test
  fun `whitespace between a catch keyword and the opening paren should fail`() {
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
  fun `more than one whitespace between a catch keyword and the opening paren should fail`() {
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
  fun `whitespace between a multiline catch keyword and the opening paren should fail`() {
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
  fun `more than one whitespace between a multiline catch keyword and the opening paren should fail`() {
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
  fun `whitespace between the first catch keyword and the opening parens should fail but no whitespace between other catch keywords and their opening parens should not fail`() {
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
  fun `more than one whitespace between the first catch keyword and the opening parens should fail but no whitespace between other catch keywords and their opening parens should not fail`() {
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
  fun `whitespace between the first multiline catch keyword and the opening parens should fail but no whitespace between other multiline catch keywords and their opening parens should not fail`() {
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
  fun `more than one whitespace between the first multiline catch keyword and the opening parens should fail but no whitespace between other multiline catch keywords and their opening parens should not fail`() {
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
  fun `no whitespace between the first catch keyword and the opening parens should not fail but whitespace between other catch keywords and their opening parens should fail`() {
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
  fun `no whitespace between the first catch keyword and the opening parens should not fail but more than one whitespace between other catch keywords and their opening parens should fail`() {
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
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but whitespace between other multiline catch keywords and their opening parens should fail`() {
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
  fun `no whitespace between the first multiline catch keyword and the opening parens should not fail but more than one whitespace between other multiline catch keywords and their opening parens should fail`() {
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
  fun `whitespace between multiple catch keywords and their opening parens should fail`() {
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
  fun `more than one whitespace between multiple catch keywords and their opening parens should fail`() {
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
  fun `whitespace between multiple multiline catch keywords and their opening parens should fail`() {
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
  fun `more than one whitespace between multiline multiple catch keywords and their opening parens should fail`() {
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
  fun `no whitespace between a catch keyword and the opening paren should not fail`() {
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
  fun `no whitespace between a multiline catch keyword and the opening paren should not fail`() {
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
  fun `no whitespace between multiple multiline catch keywords and their opening parens should not fail`() {
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
