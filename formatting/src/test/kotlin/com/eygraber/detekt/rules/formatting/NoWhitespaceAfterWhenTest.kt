package com.eygraber.detekt.rules.formatting

import com.eygraber.detekt.rules.common.test.hasStartSourceLocation
import dev.detekt.test.assertj.assertThat
import dev.detekt.test.lint
import org.junit.Test

class NoWhitespaceAfterWhenTest {
  private val rule = NoWhitespaceAfterKeyword()

  @Test
  fun `whitespace between a when keyword with a reference subject and the opening paren should fail`() {
    val code =
      """
      |fun foo() {
      |  val bar = ""
      |  when (bar) {
      |    else -> println("baz")
      |  }
      |}
      """.trimMargin()

    val findings = rule.lint(code)

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 3, column = 3)
  }

  @Test
  fun `more than one whitespace between a when keyword with a reference subject and the opening paren should fail`() {
    val code =
      """
      |fun foo() {
      |  val bar = ""
      |  when   (bar) {
      |    else -> println("baz")
      |  }
      |}
      """.trimMargin()

    val findings = rule.lint(code)

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 3, column = 3)
  }

  @Test
  fun `no whitespace between a when keyword with a reference subject and the opening paren should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  val bar = ""
      |  when(bar) {
      |    else -> println("baz")
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `whitespace between a when keyword with an inlined reference subject and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when (val bar = "baz") {
      |    else -> println(bar)
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a when keyword with an inlined reference subject and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when   (val bar = "baz") {
      |    else -> println(bar)
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between a when keyword with an inlined reference subject and the opening paren should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when(val bar = "baz") {
      |    else -> println(bar)
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `whitespace between a when keyword with a string literal subject and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when ("") {
      |    else -> println("bar")
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a when keyword with a string literal subject and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when   ("") {
      |    else -> println("bar")
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between a when keyword with a string literal subject and the opening paren should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when("") {
      |    else -> println("bar")
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `whitespace between a when keyword with a number literal subject and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when (1) {
      |    else -> println("bar")
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `more than one whitespace between a when keyword with a number literal subject and the opening paren should fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when   (1) {
      |    else -> println("bar")
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 2, column = 3)
  }

  @Test
  fun `no whitespace between a when keyword with a number literal subject and the opening paren should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when(1) {
      |    else -> println("bar")
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `whitespace between a when keyword without a subject and the opening brace should not fail`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  when {
      |    "".isEmpty() -> println("baz")
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }
}
