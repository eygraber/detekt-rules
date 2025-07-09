package com.eygraber.detekt.rules.style

import com.eygraber.detekt.rules.common.test.assertFormat
import io.gitlab.arturbosch.detekt.api.SourceLocation
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NewlineForMultilineCatchFinallyAutoCorrectTest {
  private val rule = NewlineForMultilineKeyword(
    TestConfig("autoCorrect" to true),
  )

  @Test
  fun `no newline before finally should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before finally should fail wwith autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before finally should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |
      |  } finally {
      |
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |
      |  }
      |  finally {
      |
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before finally should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |
      |  } finally {
      |
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |
      |  }
      |finally {
      |
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `newline before finally should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  finally {
      | 
      |  }  
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before catch should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before catch should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  } catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `newline before catch should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before catch with finally should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before catch with finally should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch with finally should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  } catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `newline before catch with finally should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before finally with a catch should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 7, column = 5)
  }

  @Test
  fun `no newline before finally with a catch should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |
      |  }
      |  catch(_: Throwable) {
      |
      |  } finally {
      |
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |
      |  }
      |  catch(_: Throwable) {
      |
      |  }
      |  finally {
      |
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before finally with a catch should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |
      |  }
      |  catch(_: Throwable) {
      |
      |  } finally {
      |
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |
      |  }
      |  catch(_: Throwable) {
      |
      |  }
      |finally {
      |
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch and finally should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 4, column = 5),
      SourceLocation(line = 6, column = 5),
    )
  }

  @Test
  fun `no newline before catch and finally should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |
      |  } catch(_: Throwable) {
      |
      |  } finally {
      |
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |
      |  }
      |  catch(_: Throwable) {
      |
      |  }
      |  finally {
      |
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch and finally should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |
      |  } catch(_: Throwable) {
      |
      |  } finally {
      |
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |
      |  }
      |catch(_: Throwable) {
      |
      |  }
      |finally {
      |
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `newline before finally with a catch should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      | 
      |  }  
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before multiple catches should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 4, column = 5),
      SourceLocation(line = 6, column = 5),
    )
  }

  @Test
  fun `no newline before multiple catches should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before multiple catches should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  } catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |catch(_: Exception) {
      |  
      |  }
      |catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `newline before multiple catches should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before multiple catches and finally should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(3)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 4, column = 5),
      SourceLocation(line = 6, column = 5),
      SourceLocation(line = 8, column = 5),
    )
  }

  @Test
  fun `no newline before multiple catches and finally should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before multiple catches and finally should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  } catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |catch(_: Exception) {
      |  
      |  }
      |catch(_: Throwable) {
      |  
      |  }
      |finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before finally with multiple catches should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 10, column = 5)
  }

  @Test
  fun `no newline before finally with multiple catches should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before finally with multiple catches should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch with catch before should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 7, column = 5)
  }

  @Test
  fun `no newline before catch with catch after should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before catch with catch before should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch with catch before should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch with catch after should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch with catch after should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  } catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `newline before all catches should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before catch and finally with catch before should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 7, column = 5),
      SourceLocation(line = 9, column = 5),
    )
  }

  @Test
  fun `no newline before catch and finally with catch before should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch and finally with catch before should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |catch(_: Throwable) {
      |  
      |  }
      |finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch and finally with catch after should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 4, column = 5),
      SourceLocation(line = 9, column = 5),
    )
  }

  @Test
  fun `no newline before catch and finally with catch after should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch and finally with catch after should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  } catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  } finally {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before multiple catches with finally should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasStartSourceLocations(
      SourceLocation(line = 4, column = 5),
      SourceLocation(line = 6, column = 5),
    )
  }

  @Test
  fun `no newline before multiple catches with finally should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before multiple catches with finally should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  } catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |catch(_: Exception) {
      |  
      |  }
      |catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch with catch before and finally should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 7, column = 5)
  }

  @Test
  fun `no newline before catch with catch after and finally should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }
      |}
      """.trimMargin(),
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasStartSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before catch with catch before and finally should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch with catch before and finally should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  } catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch with catch after and finally should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  try {
      |   
      |  } catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `no newline before catch with catch after and finally should format with the indent from the try with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |try {
      |   
      |  } catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |try {
      |   
      |  }
      |catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )
  }

  @Test
  fun `newline before multiple catches with finally should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  try {
      |   
      |  }
      |  catch(_: Exception) {
      |  
      |  }
      |  catch(_: Throwable) {
      |  
      |  }
      |  finally {
      |  
      |  }  
      |}
      """.trimMargin(),
    )

    assertThat(findings).isEmpty()
  }
}
