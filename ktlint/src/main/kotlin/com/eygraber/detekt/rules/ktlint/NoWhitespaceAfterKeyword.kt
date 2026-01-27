package com.eygraber.detekt.rules.ktlint

import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CATCH
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DO_WHILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LPAR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHILE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.ifAutocorrectAllowed
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpace
import com.pinterest.ktlint.rule.engine.core.api.nextSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.lexer.KtTokens

public class NoWhitespaceAfterKeyword :
  Rule(
    ruleId = RuleId("${RuleSetId.VALUE}:no-whitespace-after-keyword"),
    about = About(
      maintainer = "eygraber",
      repositoryUrl = "https://github.com/eygraber/detekt-rules",
    ),
  ),
  RuleAutocorrectApproveHandler {

  override fun beforeVisitChildNodes(
    node: ASTNode,
    emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> AutocorrectDecision,
  ) {
    when(node.elementType) {
      IF -> checkKeywordSpacing(node, KtTokens.IF_KEYWORD, emit)
      WHEN -> checkWhenKeywordSpacing(node, emit)
      FOR -> checkKeywordSpacing(node, KtTokens.FOR_KEYWORD, emit)
      WHILE -> checkWhileKeywordSpacing(node, emit)
      DO_WHILE -> checkDoWhileKeywordSpacing(node, emit)
      CATCH -> checkCatchKeywordSpacing(node, emit)
    }
  }

  private fun checkKeywordSpacing(
    node: ASTNode,
    keywordType: IElementType,
    emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> AutocorrectDecision,
  ) {
    val keyword = node.findChildByType(keywordType) ?: return
    val nextSibling = keyword.nextSibling { !it.isWhiteSpace() }
    val whitespace = keyword.treeNext

    if(whitespace != null && whitespace.isWhiteSpace() && whitespace != nextSibling) {
      emit(
        keyword.startOffset,
        "Unexpected whitespace after '${keyword.text}'",
        true,
      ).ifAutocorrectAllowed {
        removeWhitespaceBetween(keyword, nextSibling)
      }
    }
  }

  private fun checkWhenKeywordSpacing(
    node: ASTNode,
    emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> AutocorrectDecision,
  ) {
    // Only check `when` with a subject (has parentheses)
    val hasSubject = node.findChildByType(LPAR) != null
    if(!hasSubject) return

    val keyword = node.findChildByType(KtTokens.WHEN_KEYWORD) ?: return
    val nextSibling = keyword.nextSibling { !it.isWhiteSpace() }
    val whitespace = keyword.treeNext

    if(whitespace != null && whitespace.isWhiteSpace() && whitespace != nextSibling) {
      emit(
        keyword.startOffset,
        "Unexpected whitespace after 'when'",
        true,
      ).ifAutocorrectAllowed {
        removeWhitespaceBetween(keyword, nextSibling)
      }
    }
  }

  private fun checkWhileKeywordSpacing(
    node: ASTNode,
    emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> AutocorrectDecision,
  ) {
    val keyword = node.findChildByType(WHILE_KEYWORD) ?: return
    val nextSibling = keyword.nextSibling { !it.isWhiteSpace() }
    val whitespace = keyword.treeNext

    if(whitespace != null && whitespace.isWhiteSpace() && whitespace != nextSibling) {
      emit(
        keyword.startOffset,
        "Unexpected whitespace after 'while'",
        true,
      ).ifAutocorrectAllowed {
        removeWhitespaceBetween(keyword, nextSibling)
      }
    }
  }

  private fun checkDoWhileKeywordSpacing(
    node: ASTNode,
    emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> AutocorrectDecision,
  ) {
    val keyword = node.findChildByType(WHILE_KEYWORD) ?: return
    val nextSibling = keyword.nextSibling { !it.isWhiteSpace() }
    val whitespace = keyword.treeNext

    if(whitespace != null && whitespace.isWhiteSpace() && whitespace != nextSibling) {
      emit(
        keyword.startOffset,
        "Unexpected whitespace after 'while' in do-while",
        true,
      ).ifAutocorrectAllowed {
        removeWhitespaceBetween(keyword, nextSibling)
      }
    }
  }

  private fun checkCatchKeywordSpacing(
    node: ASTNode,
    emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> AutocorrectDecision,
  ) {
    val keyword = node.findChildByType(KtTokens.CATCH_KEYWORD) ?: return
    val nextSibling = keyword.nextSibling { !it.isWhiteSpace() }
    val whitespace = keyword.treeNext

    if(whitespace != null && whitespace.isWhiteSpace() && whitespace != nextSibling) {
      emit(
        keyword.startOffset,
        "Unexpected whitespace after 'catch'",
        true,
      ).ifAutocorrectAllowed {
        removeWhitespaceBetween(keyword, nextSibling)
      }
    }
  }

  private fun removeWhitespaceBetween(keyword: ASTNode, nextNonWhitespace: ASTNode?) {
    var current = keyword.treeNext
    while(current != null && current.isWhiteSpace() && current != nextNonWhitespace) {
      val next = current.treeNext
      current.treeParent.removeChild(current)
      current = next
    }
  }
}
