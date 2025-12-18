package com.eygraber.detekt.rules.formatting

import com.intellij.psi.PsiWhiteSpace
import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import dev.detekt.api.modifiedText
import org.jetbrains.kotlin.psi.KtDoWhileExpression
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtForExpression
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.KtTryExpression
import org.jetbrains.kotlin.psi.KtWhenExpression
import org.jetbrains.kotlin.psi.KtWhileExpression

public class NoWhitespaceAfterKeyword(
  ruleSetConfig: Config = Config.empty,
) : Rule(
  config = ruleSetConfig,
  description = "Reports improper spacing around keywords",
) {
  private lateinit var workingFile: KtFile
  private lateinit var originalFile: KtFile

  override fun visit(root: KtFile) {
    if(autoCorrect) {
      // Create a writable copy of the file for autocorrect
      val fileCopy = KtPsiFactory(root.project).createPhysicalFile(
        fileName = root.name,
        text = root.modifiedText ?: root.text,
      )

      workingFile = fileCopy
      originalFile = root
    }
    else {
      workingFile = root
      originalFile = root
    }

    super.visit(workingFile)

    if(autoCorrect && workingFile.modificationStamp > 0) {
      originalFile.modifiedText = workingFile.text
    }
  }

  override fun visitWhenExpression(expression: KtWhenExpression) {
    val hasASubject = expression.children.any { it is KtExpression }
    if(hasASubject && expression.whenKeyword.nextSibling is PsiWhiteSpace) {
      report(expression)

      if(autoCorrect) {
        val whitespace = expression.whenKeyword.nextSibling
        whitespace.parent.node.removeChild(whitespace.node)
      }
    }
  }

  override fun visitIfExpression(expression: KtIfExpression) {
    if(expression.ifKeyword.nextSibling is PsiWhiteSpace) {
      report(expression)

      if(autoCorrect) {
        var whitespace = expression.ifKeyword.nextSibling
        while(whitespace != null && whitespace is PsiWhiteSpace) {
          whitespace.parent.node.removeChild(whitespace.node)
          whitespace = expression.ifKeyword.nextSibling
        }
      }
    }

    super.visitIfExpression(expression)
  }

  override fun visitForExpression(expression: KtForExpression) {
    val hasASubject = expression.children.any { it is KtExpression }
    if(hasASubject && expression.forKeyword.nextSibling is PsiWhiteSpace) {
      report(expression)

      if(autoCorrect) {
        val whitespace = expression.forKeyword.nextSibling
        whitespace.parent.node.removeChild(whitespace.node)
      }
    }
  }

  override fun visitWhileExpression(expression: KtWhileExpression) {
    if(expression.leftParenthesis?.prevSibling?.text != "while") {
      report(expression)

      if(autoCorrect) {
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

      if(autoCorrect) {
        val whitespace = expression.whileKeyword?.nextSibling
        whitespace?.parent?.node?.removeChild(whitespace.node)
      }
    }
  }

  override fun visitTryExpression(expression: KtTryExpression) {
    expression.catchClauses.forEach { catch ->
      if(catch.firstChild.nextSibling is PsiWhiteSpace) {
        report(expression)

        if(autoCorrect) {
          val whitespace = catch.firstChild.nextSibling
          whitespace.parent?.node?.removeChild(whitespace.node)
        }
      }
    }
  }

  private fun report(expression: KtExpression) {
    report(
      Finding(
        entity = Entity.from(expression),
        message = description,
      ),
    )
  }
}
