package com.eygraber.detekt.rules.ktlint

import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CATCH
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FINALLY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TRY
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.ifAutocorrectAllowed
import com.pinterest.ktlint.rule.engine.core.api.indent
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpace
import com.pinterest.ktlint.rule.engine.core.api.prevSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.lexer.KtTokens

public class NewlineForMultilineKeyword :
  Rule(
    ruleId = RuleId("${RuleSetId.VALUE}:newline-for-multiline-keyword"),
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
      IF -> checkIfExpression(node, emit)
      TRY -> checkTryExpression(node, emit)
    }
  }

  private fun checkIfExpression(
    node: ASTNode,
    emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> AutocorrectDecision,
  ) {
    val elseNode = node.findChildByType(ELSE) ?: return
    val thenNode = node.findChildByType(THEN) ?: return

    // Check if then branch is a block (multiline construct)
    val thenBlock = thenNode.findChildByType(BLOCK) ?: return

    // Get the else keyword
    val elseKeyword = node.findChildByType(KtTokens.ELSE_KEYWORD) ?: return

    // Check if else branch is a block or an if expression (else if)
    val elseContent = elseNode.firstChildNode
    val isElseBlock = elseContent?.elementType == BLOCK
    val isElseIf = elseContent?.elementType == IF

    // For else if, check that the nested if has a block as then
    if(isElseIf) {
      val nestedThen = elseContent?.findChildByType(THEN)
      val nestedThenBlock = nestedThen?.findChildByType(BLOCK)
      if(nestedThenBlock == null) return
    }
    else if(!isElseBlock) {
      return
    }

    // Check if else keyword is preceded by a newline
    val prevSibling = elseKeyword.prevSibling { !it.isWhiteSpace() }
    val whitespace = elseKeyword.treePrev

    if(whitespace != null && whitespace.isWhiteSpace()) {
      val text = whitespace.text
      if(!text.startsWith('\n') && !text.startsWith("\r\n")) {
        emit(
          elseKeyword.startOffset,
          "Expected newline before 'else'",
          true,
        ).ifAutocorrectAllowed {
          val indent = node.indent(includeNewline = false)
          (whitespace as? LeafPsiElement)?.rawReplaceWithText("\n$indent")
        }
      }
    }
  }

  private fun checkTryExpression(
    node: ASTNode,
    emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> AutocorrectDecision,
  ) {
    // Check catch clauses
    node.getChildren(null).filter { it.elementType == CATCH }.forEach { catchNode ->
      val catchKeyword = catchNode.findChildByType(KtTokens.CATCH_KEYWORD) ?: return@forEach

      val whitespace = catchNode.treePrev
      if(whitespace != null && whitespace.isWhiteSpace()) {
        val text = whitespace.text
        if(!text.startsWith('\n') && !text.startsWith("\r\n")) {
          emit(
            catchKeyword.startOffset,
            "Expected newline before 'catch'",
            true,
          ).ifAutocorrectAllowed {
            val indent = node.indent(includeNewline = false)
            (whitespace as? LeafPsiElement)?.rawReplaceWithText("\n$indent")
          }
        }
      }
    }

    // Check finally block
    val finallyNode = node.findChildByType(FINALLY) ?: return
    val finallyKeyword = finallyNode.findChildByType(KtTokens.FINALLY_KEYWORD) ?: return

    val whitespace = finallyNode.treePrev
    if(whitespace != null && whitespace.isWhiteSpace()) {
      val text = whitespace.text
      if(!text.startsWith('\n') && !text.startsWith("\r\n")) {
        emit(
          finallyKeyword.startOffset,
          "Expected newline before 'finally'",
          true,
        ).ifAutocorrectAllowed {
          val indent = node.indent(includeNewline = false)
          (whitespace as? LeafPsiElement)?.rawReplaceWithText("\n$indent")
        }
      }
    }
  }
}
