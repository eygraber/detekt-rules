package com.eygraber.detekt.rules.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import org.junit.Test

class NoWhitespaceAfterKeywordTest {
  private val wrappedRule = assertThatRule { NoWhitespaceAfterKeyword() }

  // IF tests

  @Test
  fun `no whitespace after if keyword should pass`() {
    val code = """
      fun foo() {
        if(true) {}
      }
    """.trimIndent()
    wrappedRule.withEditorConfigOverride(CODE_STYLE_PROPERTY to "intellij_idea")
      .assertThat(code)
      .hasNoLintViolations()
  }

  @Test
  fun `whitespace after if keyword should fail and be autocorrected`() {
    val code = """
      fun foo() {
        if (true) {}
      }
    """.trimIndent()
    val expected = """
      fun foo() {
        if(true) {}
      }
    """.trimIndent()
    wrappedRule
      .assertThat(code)
      .hasLintViolation(2, 3, "Unexpected whitespace after 'if'")
      .isFormattedAs(expected)
  }

  @Test
  fun `multiple whitespaces after if keyword should fail and be autocorrected`() {
    val code = """
      fun foo() {
        if   (true) {}
      }
    """.trimIndent()
    val expected = """
      fun foo() {
        if(true) {}
      }
    """.trimIndent()
    wrappedRule
      .assertThat(code)
      .hasLintViolation(2, 3, "Unexpected whitespace after 'if'")
      .isFormattedAs(expected)
  }

  // WHEN tests

  @Test
  fun `no whitespace after when keyword with subject should pass`() {
    val code = """
      fun foo(x: Int) {
        when(x) {
          1 -> {}
        }
      }
    """.trimIndent()
    wrappedRule.withEditorConfigOverride(CODE_STYLE_PROPERTY to "intellij_idea")
      .assertThat(code)
      .hasNoLintViolations()
  }

  @Test
  fun `whitespace after when keyword with subject should fail and be autocorrected`() {
    val code = """
      fun foo(x: Int) {
        when (x) {
          1 -> {}
        }
      }
    """.trimIndent()
    val expected = """
      fun foo(x: Int) {
        when(x) {
          1 -> {}
        }
      }
    """.trimIndent()
    wrappedRule
      .assertThat(code)
      .hasLintViolation(2, 3, "Unexpected whitespace after 'when'")
      .isFormattedAs(expected)
  }

  @Test
  fun `when without subject should pass even with whitespace`() {
    val code = """
      fun foo(x: Int) {
        when {
          x > 0 -> {}
        }
      }
    """.trimIndent()
    wrappedRule
      .assertThat(code)
      .hasNoLintViolations()
  }

  // FOR tests

  @Test
  fun `no whitespace after for keyword should pass`() {
    val code = """
      fun foo() {
        for(i in 1..10) {}
      }
    """.trimIndent()
    wrappedRule.withEditorConfigOverride(CODE_STYLE_PROPERTY to "intellij_idea")
      .assertThat(code)
      .hasNoLintViolations()
  }

  @Test
  fun `whitespace after for keyword should fail and be autocorrected`() {
    val code = """
      fun foo() {
        for (i in 1..10) {}
      }
    """.trimIndent()
    val expected = """
      fun foo() {
        for(i in 1..10) {}
      }
    """.trimIndent()
    wrappedRule
      .assertThat(code)
      .hasLintViolation(2, 3, "Unexpected whitespace after 'for'")
      .isFormattedAs(expected)
  }

  // WHILE tests

  @Test
  fun `no whitespace after while keyword should pass`() {
    val code = """
      fun foo() {
        while(true) {}
      }
    """.trimIndent()
    wrappedRule.withEditorConfigOverride(CODE_STYLE_PROPERTY to "intellij_idea")
      .assertThat(code)
      .hasNoLintViolations()
  }

  @Test
  fun `whitespace after while keyword should fail and be autocorrected`() {
    val code = """
      fun foo() {
        while (true) {}
      }
    """.trimIndent()
    val expected = """
      fun foo() {
        while(true) {}
      }
    """.trimIndent()
    wrappedRule
      .assertThat(code)
      .hasLintViolation(2, 3, "Unexpected whitespace after 'while'")
      .isFormattedAs(expected)
  }

  // DO-WHILE tests

  @Test
  fun `no whitespace after while in do-while should pass`() {
    val code = """
      fun foo() {
        do {} while(true)
      }
    """.trimIndent()
    wrappedRule.withEditorConfigOverride(CODE_STYLE_PROPERTY to "intellij_idea")
      .assertThat(code)
      .hasNoLintViolations()
  }

  @Test
  fun `whitespace after while in do-while should fail and be autocorrected`() {
    val code = """
      fun foo() {
        do {} while (true)
      }
    """.trimIndent()
    val expected = """
      fun foo() {
        do {} while(true)
      }
    """.trimIndent()
    wrappedRule
      .assertThat(code)
      .hasLintViolation(2, 3, "Unexpected whitespace after 'while' in do-while")
      .isFormattedAs(expected)
  }

  // CATCH tests

  @Test
  fun `no whitespace after catch keyword should pass`() {
    val code = """
      fun foo() {
        try {} catch(e: Exception) {}
      }
    """.trimIndent()
    wrappedRule.withEditorConfigOverride(CODE_STYLE_PROPERTY to "intellij_idea")
      .assertThat(code)
      .hasNoLintViolations()
  }

  @Test
  fun `whitespace after catch keyword should fail and be autocorrected`() {
    val code = """
      fun foo() {
        try {} catch (e: Exception) {}
      }
    """.trimIndent()
    val expected = """
      fun foo() {
        try {} catch(e: Exception) {}
      }
    """.trimIndent()
    wrappedRule
      .assertThat(code)
      .hasLintViolation(2, 10, "Unexpected whitespace after 'catch'")
      .isFormattedAs(expected)
  }

  companion object {
    private val CODE_STYLE_PROPERTY =
      com.pinterest.ktlint.rule.engine.core.api.editorconfig.CODE_STYLE_PROPERTY
  }
}
