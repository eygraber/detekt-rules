package com.eygraber.detekt.rules.common.test

import dev.detekt.api.Rule
import dev.detekt.api.SourceLocation
import dev.detekt.api.modifiedText
import dev.detekt.test.assertj.FindingsAssert
import dev.detekt.test.location
import dev.detekt.test.utils.compileContentForTest
import org.intellij.lang.annotations.Language
import java.util.Objects

public fun Rule.assertFormat(
  @Language("kotlin") input: String,
  @Language("kotlin") expected: String,
) {
  val ktFile = compileContentForTest(input)

  visit(ktFile)
  val actualText = ktFile.modifiedText ?: ktFile.text
  assert(actualText == expected) {
    """
    |Output does not match expected:
    |$actualText
    |
    |$expected
    |
    """.trimMargin()
  }
}

/**
 * Language Injection: Imports show "Package directive and imports are forbidden in code fragments" in injected files
 *
 * [https://youtrack.jetbrains.com/issue/KTIJ-32613](KTIJ-32613)
 */
public const val IMPORT_ISE: String = """
  import java.lang.IllegalStateException"""

public fun FindingsAssert.hasStartSourceLocations(vararg expected: SourceLocation) {
  apply {
    val actualSources = actual().asSequence()
      .map { it.location.source }
      .sortedWith(compareBy({ it.line }, { it.column }))

    val expectedSources = expected.asSequence()
      .sortedWith(compareBy({ it.line }, { it.column }))

    if(!Objects.deepEquals(actualSources.toList(), expectedSources.toList())) {
      error("Expected start source locations to be ${expectedSources.toList()} but was ${actualSources.toList()}")
    }
  }
}

public fun FindingsAssert.hasStartSourceLocation(
  line: Int,
  column: Int,
) {
  first().hasStartSourceLocation(line, column)
}
