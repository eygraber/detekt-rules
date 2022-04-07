package com.eygraber.detekt.rules.common.test

import io.github.detekt.test.utils.compileContentForTest
import io.gitlab.arturbosch.detekt.api.Rule
import org.intellij.lang.annotations.Language

fun Rule.assertFormat(
  @Language("kotlin") input: String,
  @Language("kotlin") expected: String,
) {
  val ktFile = compileContentForTest(input)

  visit(ktFile)
  assert(ktFile.text == expected) {
    """
      |Output does not match expected:
      |${ktFile.text}
      |
      |$expected
      |
    """.trimMargin()
  }
}
