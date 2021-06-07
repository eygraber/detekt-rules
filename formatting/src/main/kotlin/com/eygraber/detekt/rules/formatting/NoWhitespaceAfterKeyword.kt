package com.eygraber.detekt.rules.formatting

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.psi.KtDoWhileExpression
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtForExpression
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.kotlin.psi.KtTryExpression
import org.jetbrains.kotlin.psi.KtWhenExpression
import org.jetbrains.kotlin.psi.KtWhileExpression

class NoWhitespaceAfterKeyword(
  ruleSetConfig: Config = Config.empty
) : Rule(ruleSetConfig) {
  override val issue = Issue(
    javaClass.simpleName,
    Severity.Style,
    "Reports improper spacing around keywords",
    Debt.FIVE_MINS
  )

  override fun visitWhenExpression(expression: KtWhenExpression) {
    val hasASubject = expression.children.any { it is KtExpression }
    if(hasASubject && expression.whenKeyword.nextSibling is PsiWhiteSpace) {
      report(expression)

      withAutoCorrect {
        val whitespace = expression.whenKeyword.nextSibling
        whitespace.parent.node.removeChild(whitespace.node)
      }
    }
  }

  override fun visitIfExpression(expression: KtIfExpression) {
    if(expression.ifKeyword.nextSibling is PsiWhiteSpace) {
      report(expression)

      withAutoCorrect {
        var whitespace = expression.ifKeyword.nextSibling
        while(whitespace != null && whitespace is PsiWhiteSpace) {
          whitespace.parent.node.removeChild(whitespace.node)
          whitespace = expression.ifKeyword.nextSibling
        }
      }
    }

    expression.`else`?.firstChild?.let { elseChild ->
      if(elseChild.text == "if") {
        visitIfExpression(KtIfExpression(elseChild.parent.node))
      }
    }
  }

  override fun visitForExpression(expression: KtForExpression) {
    val hasASubject = expression.children.any { it is KtExpression }
    if(hasASubject && expression.forKeyword.nextSibling is PsiWhiteSpace) {
      report(expression)

      withAutoCorrect {
        val whitespace = expression.forKeyword.nextSibling
        whitespace.parent.node.removeChild(whitespace.node)
      }
    }
  }

  override fun visitWhileExpression(expression: KtWhileExpression) {
    if(expression.leftParenthesis?.prevSibling?.text != "while") {
      report(expression)

      withAutoCorrect {
        var notWhile = expression.leftParenthesis?.prevSibling
        while(notWhile != null && notWhile.text != "while") {
          notWhile.parent.node.removeChild(notWhile.node)
          notWhile = expression.leftParenthesis?.prevSibling
        }
      }
    }
  }

  override fun visitDoWhileExpression(expression: KtDoWhileExpression) {
    if(expression.whileKeyword?.nextSibling is PsiWhiteSpace) {
      report(expression)

      withAutoCorrect {
        val whitespace = expression.whileKeyword?.nextSibling
        whitespace?.parent?.node?.removeChild(whitespace.node)
      }
    }
  }

  override fun visitTryExpression(expression: KtTryExpression) {
    expression.catchClauses.forEach { catch ->
      if(catch.firstChild.nextSibling is PsiWhiteSpace) {
        report(expression)

        withAutoCorrect {
          val whitespace = catch.firstChild.nextSibling
          whitespace.parent?.node?.removeChild(whitespace.node)
        }
      }
    }
  }

  private fun report(expression: KtExpression) {
    report(
      CodeSmell(
        issue,
        Entity.from(expression),
        issue.description
      )
    )
  }
}
