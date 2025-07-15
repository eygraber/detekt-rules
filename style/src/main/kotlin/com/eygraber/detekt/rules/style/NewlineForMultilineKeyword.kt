package com.eygraber.detekt.rules.style

import com.pinterest.ktlint.rule.engine.core.api.indentWithoutNewlinePrefix
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.kotlin.psi.KtTryExpression

public class NewlineForMultilineKeyword(
  ruleSetConfig: Config = Config.empty,
) : Rule(ruleSetConfig) {
  override val issue: Issue = Issue(
    javaClass.simpleName,
    Severity.Style,
    "Multiline keyword was found that was not on a new line.",
    Debt.FIVE_MINS,
  )

  override fun visitIfExpression(expression: KtIfExpression) {
    // println(expression.elseKeyword?.prevSibling?.text?.contains("\n") == true)
    val elseExpression = expression.`else` ?: return

    val shouldHandle = expression.then is KtBlockExpression && when(elseExpression) {
      is KtIfExpression -> elseExpression.then is KtBlockExpression // handles else if
      else -> elseExpression is KtBlockExpression
    }

    if(shouldHandle) {
      val elseKeyword = expression.elseKeyword ?: return
      if(!elseKeyword.prevSibling.text.startsWith('\n')) {
        report(elseKeyword)

        withAutoCorrect {
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

        withAutoCorrect {
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

        withAutoCorrect {
          autoCorrects += {
            finallyBlock.prevSibling.node.treeParent.replaceChild(
              finallyBlock.prevSibling.node,
              PsiWhiteSpaceImpl("\n${expression.node.indentWithoutNewlinePrefix}"),
            )
          }
        }
      }
    }

    withAutoCorrect {
      for(autoCorrect in autoCorrects) {
        autoCorrect()
      }
    }
  }

  private fun report(element: PsiElement) {
    report(
      CodeSmell(
        issue,
        Entity.from(element),
        issue.description,
      ),
    )
  }
}
