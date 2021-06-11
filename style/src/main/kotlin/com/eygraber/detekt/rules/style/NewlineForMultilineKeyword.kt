package com.eygraber.detekt.rules.style

import com.pinterest.ktlint.core.KtLint
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

class NewlineForMultilineKeyword(
  ruleSetConfig: Config = Config.empty
) : Rule(ruleSetConfig) {
  override val issue = Issue(
    javaClass.simpleName,
    Severity.Style,
    "Multiline keyword was found that was not on a new line.",
    Debt.FIVE_MINS
  )

  override fun visitIfExpression(expression: KtIfExpression) {
    val elseExpression = expression.`else` ?: return
    if(expression.then is KtBlockExpression && elseExpression is KtBlockExpression) {
      val elseKeyword = expression.elseKeyword ?: return
      if(!elseKeyword.prevSibling.text.startsWith('\n')) {
        report(elseKeyword)

        val indentSize = elseKeyword.node.getUserData(KtLint.EDITOR_CONFIG_USER_DATA_KEY)?.indentSize ?: 2
        if(indentSize != -1) {
          withAutoCorrect {
            elseKeyword.prevSibling.node.treeParent.replaceChild(
              elseKeyword.prevSibling.node,
              PsiWhiteSpaceImpl("\n${" ".repeat(indentSize)}")
            )
          }
        }
      }
    }
  }

  override fun visitTryExpression(expression: KtTryExpression) {
    val autoCorrects = mutableListOf<() -> Unit>()

    expression.catchClauses.forEach { catch ->
      if(!catch.prevSibling.text.startsWith('\n')) {
        report(catch)

        withAutoCorrect {
          val indentSize = catch.node.getUserData(KtLint.EDITOR_CONFIG_USER_DATA_KEY)?.indentSize ?: 2
          if(indentSize != -1) {
            autoCorrects += {
              catch.prevSibling.node.treeParent.replaceChild(
                catch.prevSibling.node,
                PsiWhiteSpaceImpl("\n${" ".repeat(indentSize)}")
              )
            }
          }
        }
      }
    }

    expression.finallyBlock?.let { finallyBlock ->
      if(!finallyBlock.prevSibling.text.startsWith('\n')) {
        report(finallyBlock)

        withAutoCorrect {
          val indentSize = finallyBlock.node.getUserData(KtLint.EDITOR_CONFIG_USER_DATA_KEY)?.indentSize ?: 2
          if(indentSize != -1) {
            autoCorrects += {
              finallyBlock.prevSibling.node.treeParent.replaceChild(
                finallyBlock.prevSibling.node,
                PsiWhiteSpaceImpl("\n${" ".repeat(indentSize)}")
              )
            }
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
        issue.description
      )
    )
  }
}
