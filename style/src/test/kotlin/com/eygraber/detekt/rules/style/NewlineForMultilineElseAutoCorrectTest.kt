package com.eygraber.detekt.rules.style

import com.eygraber.detekt.rules.common.test.assertFormat
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NewlineForMultilineElseAutoCorrectTest {
  private val rule = NewlineForMultilineKeyword(
    TestConfig("autoCorrect" to true)
  )

  @Test
  fun `no newline before else should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  } else {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 4, column = 5)
  }

  @Test
  fun `no newline before else should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {
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
      |  else {
      |  
      |  }
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `no newline before else should format with the indent from the if with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |if(true) {
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
      |else {
      |  
      |  }
      |}
      """.trimMargin()
    )
  }

  @Test
  fun `newline before else should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `no newline before else with else if should fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) {
      |  
      |  }
      |  else if(false) {
      |   
      |  } else {
      |  
      |  }
      |}
      """.trimMargin()
    )

    assertThat(findings).hasSize(1)
    assertThat(findings).hasSourceLocation(line = 7, column = 5)
  }

  @Test
  fun `no newline before else with else if should format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) {
      |   
      |  }
      |  else if(false) {
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
  fun `no newline before else with else if should format with the indent from the if with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |if(true) {
      |   
      |  }
      |else if(false) {
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
  fun `newline before else with else if should not fail with autocorrect`() {
    val findings = rule.lint(
      """
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

    assertThat(findings).isEmpty()
  }

  @Test
  fun `single line if else should not fail with autocorrect`() {
    val findings = rule.lint(
      """
      |fun foo() {
      |  if(true) continue else continue
      |}
      """.trimMargin()
    )

    assertThat(findings).isEmpty()
  }

  @Test
  fun `single line if else should not format with autocorrect`() {
    rule.assertFormat(
      input = """
      |fun foo() {
      |  if(true) continue else continue
      |}
      """.trimMargin(),

      expected = """
      |fun foo() {
      |  if(true) continue else continue
      |}
      """.trimMargin()
    )
  }
}
