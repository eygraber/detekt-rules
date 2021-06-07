package com.eygraber.detekt.rules

import io.github.detekt.test.utils.compileContentForTest
import io.gitlab.arturbosch.detekt.api.internal.BaseRule
import org.intellij.lang.annotations.Language

fun BaseRule.assertFormat(
  @Language("kotlin") input: String,
  @Language("kotlin") expected: String
) {
  val ktFile = compileContentForTest(input)
  visit(ktFile)
  assert(ktFile.text == expected) {
    """
      |Output does not match expected:
      |${ktFile.text}
      |
      |$expected
      |""".trimMargin()
  }
}
