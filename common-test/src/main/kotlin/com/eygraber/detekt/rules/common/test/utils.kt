package com.eygraber.detekt.rules.common.test

import com.pinterest.ktlint.core.EditorConfig
import com.pinterest.ktlint.core.KtLint
import io.github.detekt.test.utils.compileContentForTest
import io.gitlab.arturbosch.detekt.api.internal.BaseRule
import org.intellij.lang.annotations.Language

fun BaseRule.assertFormat(
  indentSize: Int = -1,
  @Language("kotlin") input: String,
  @Language("kotlin") expected: String,
) {
  val ktFile = compileContentForTest(input)

  if(indentSize >= 0) {
    ktFile.node.putUserData(
      KtLint.EDITOR_CONFIG_USER_DATA_KEY,
      EditorConfig.fromMap(mapOf("indent_size" to "$indentSize"))
    )
  }

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
