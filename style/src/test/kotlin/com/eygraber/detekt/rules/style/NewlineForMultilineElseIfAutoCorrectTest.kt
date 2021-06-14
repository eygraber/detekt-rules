package com.eygraber.detekt.rules.style

import com.eygraber.detekt.rules.common.test.assertFormat
import io.gitlab.arturbosch.detekt.api.SourceLocation
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NewlineForMultilineElseIfAutoCorrectTest {
  private val rule = NewlineForMultilineKeyword(
    TestConfig("autoCorrect" to true)
  )

  @Test
  fun `no newline before else if should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  } else if(false) {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before else if should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {
      |   
      |  } else if(false) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else if(false) {
      |  
      |  }
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no newline before else if should format with the indent from the if with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |if(true) {
      |   
      |  } else if(false) {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |if(true) {
      |   
      |  }
      |else if(false) {
      |  
      |  }
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `newline before else if should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else if(false) {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before else if with else should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |  
      |  } else if(false) {
      |   
      |  }
      |  else {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before else if with else should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {
      |   
      |  } else if(false) {
      |
      |  }
      |  else {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else if(false) {
      |
      |  }
      |  else {
      |  
      |  }
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no newline before else if with else should format with the indent from the if with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |if(true) {
      |   
      |  } else if(false) {
      |  
      |  }
      |  else {
      |  
      |  }
      |}  
      """.trimMargin(),

      expected = """
      |fun foo() {
      |if(true) {
      |   
      |  }
      |else if(false) {
      |  
      |  }
      |  else {
      |  
      |  }
      |}  
      """.trimMargin()
    )
  }

  @Test
  fun `no newline before else if and else should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |  
      |  } else if(false) {
      |   
      |  } else {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(2)
    assertThat(findings).hasSourceLocations(SourceLocation(line = 4, column = 5), SourceLocation(line = 6, column = 7))
  }

  @Test
  fun `no newline before else if and else should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {
      |   
      |  } else if(false) {
      |
      |  } else {
      |  
      |  }
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else if(false) {
      |
      |  }
      |  else {
      |  
      |  }
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no newline before else if and else should format with the indent from the if with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |if(true) {
      |   
      |  } else if(false) {
      |  
      |  } else {
      |  
      |  }
      |}  
      """.trimMargin(),

      expected = """
      |fun foo() {
      |if(true) {
      |   
      |  }
      |else if(false) {
      |  
      |  }
      |else {
      |  
      |  }
      |}  
      """.trimMargin()
    )
  }

  @Test
  fun `single line if else if should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) continue else if(false) continue
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `single line if else if should not format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) continue else if(false) continue
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) continue else if(false) continue
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `single line if else if else should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) continue else if(false) continue else continue
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `single line if else if else should not format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) continue else if(false) continue else continue
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) continue else if(false) continue else continue
      |}
      """.trimMargin()
    )
  }
}
