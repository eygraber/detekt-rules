package com.eygraber.detekt.rules.style

import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType.WHITE_SPACE
import org.jetbrains.kotlin.psi.psiUtil.leaves

/**
 * The indentation of [ASTNode] excluding the newline prefix
 */
internal val ASTNode.indentWithoutNewlinePrefix: String
  get(): String = indentInternal().removePrefix("\n")

/**
 * Get the current indentation of the line containing the [ASTNode]
 */
private fun ASTNode.indentInternal(): String =
  leaves(forward = false)
    .firstOrNull { it.isWhiteSpaceWithNewline20 }
    ?.text
    ?.substringAfterLast('\n')
    .orEmpty() // Fallback if node is not preceded by any newline character

/**
 * `true` when [ASTNode] is a whitespace element that contains a newline
 */
private val ASTNode?.isWhiteSpaceWithNewline20: Boolean
  get(): Boolean = this != null && isWhiteSpace20 && textContains('\n')

/**
 * `true` when [ASTNode] is a whitespace element
 */
private val ASTNode?.isWhiteSpace20: Boolean
  get() = this != null && elementType == WHITE_SPACE
