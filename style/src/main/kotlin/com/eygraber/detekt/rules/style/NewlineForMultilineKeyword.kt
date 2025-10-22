package com.eygraber.detekt.rules.style

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.kotlin.psi.KtTryExpression

public class NewlineForMultilineKeyword(
  ruleSetConfig: Config = Config.empty,
) : Rule(
  config = ruleSetConfig,
  description = "Multiline keyword was found that was not on a new line.",
) {
  override fun visitIfExpression(expression: KtIfExpression) {
    // println(expression.elseKeyword?.prevSibling?.text?.contains("\n") == true)
    val elseExpression = expression.`else` ?: return

    val shouldHandle = expression.then is KtBlockExpression &&
      when(elseExpression) {
        is KtIfExpression -> elseExpression.then is KtBlockExpression // handles else if
        else -> elseExpression is KtBlockExpression
      }

    if(shouldHandle) {
      val elseKeyword = expression.elseKeyword ?: return
      if(!elseKeyword.prevSibling.text.startsWith('\n')) {
        report(elseKeyword)

        if(autoCorrect) {
          elseKeyword.prevSibling.node.treeParent.replaceChild(
            elseKeyword.prevSibling.node,
            PsiWhiteSpaceImpl("\n${expression.node.indentWithoutNewlinePrefix}"),
          )
        }
      }
    }

    super.visitIfExpression(expression)
  }

  override fun visitTryExpression(expression: KtTryExpression) {
    val autoCorrects = mutableListOf<() -> Unit>()

    expression.catchClauses.forEach { catch ->
      if(!catch.prevSibling.text.startsWith('\n')) {
        report(catch)

        if(autoCorrect) {
          autoCorrects += {
            catch.prevSibling.node.treeParent.replaceChild(
              catch.prevSibling.node,
              PsiWhiteSpaceImpl("\n${expression.node.indentWithoutNewlinePrefix}"),
            )
          }
        }
      }
    }

    expression.finallyBlock?.let { finallyBlock ->
      if(!finallyBlock.prevSibling.text.startsWith('\n')) {
        report(finallyBlock)

        if(autoCorrect) {
          autoCorrects += {
            finallyBlock.prevSibling.node.treeParent.replaceChild(
              finallyBlock.prevSibling.node,
              PsiWhiteSpaceImpl("\n${expression.node.indentWithoutNewlinePrefix}"),
            )
          }
        }
      }
    }

    if(autoCorrect) {
      for(autoCorrect in autoCorrects) {
        autoCorrect()
      }
    }
  }

  private fun report(element: PsiElement) {
    report(
      Finding(
        entity = Entity.from(element),
        message = description,
      ),
    )
  }
}
