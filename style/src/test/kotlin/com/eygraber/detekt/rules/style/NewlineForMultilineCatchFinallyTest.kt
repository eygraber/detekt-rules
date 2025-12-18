package com.eygraber.detekt.rules.style

import com.eygraber.detekt.rules.common.test.hasStartSourceLocation
import com.eygraber.detekt.rules.common.test.hasStartSourceLocations
import dev.detekt.api.SourceLocation
import dev.detekt.test.assertj.assertThat
import dev.detekt.test.lint
import org.junit.Test

class NewlineForMultilineCatchFinallyTest {
  private val rule = NewlineForMultilineKeyword()

  @Test
  fun `no newline before finally should fail`() {
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
  fun `newline before finally should not fail`() {
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
  fun `no newline before catch should fail`() {
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
  fun `newline before catch should not fail`() {
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
  fun `no newline before catch with finally should fail`() {
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
  fun `newline before catch with finally should not fail`() {
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
  fun `no newline before finally with a catch should fail`() {
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
  fun `no newline before catch and finally should fail`() {
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
  fun `newline before finally with a catch should not fail`() {
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
  fun `no newline before multiple catches should fail`() {
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
  fun `newline before multiple catches should not fail`() {
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
  fun `no newline before multiple catches and finally should fail`() {
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
  fun `no newline before finally with multiple catches should fail`() {
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
  fun `no newline before catch with catch before should fail`() {
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
  fun `no newline before catch with catch after should fail`() {
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
  fun `newline before all catches should not fail`() {
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
  fun `no newline before catch and finally with catch before should fail`() {
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
  fun `no newline before catch and finally with catch after should fail`() {
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
  fun `no newline before multiple catches with finally should fail`() {
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
  fun `no newline before catch with catch before and finally should fail`() {
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
  fun `no newline before catch with catch after and finally should fail`() {
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
  fun `newline before multiple catches with finally should not fail`() {
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
