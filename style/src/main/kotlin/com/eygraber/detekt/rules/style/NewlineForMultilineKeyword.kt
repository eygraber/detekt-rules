package com.eygraber.detekt.rules.style

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import dev.detekt.api.modifiedText
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.KtTryExpression

public class NewlineForMultilineKeyword(
  ruleSetConfig: Config = Config.empty,
) : Rule(
  config = ruleSetConfig,
  description = "Multiline keyword was found that was not on a new line.",
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

  override fun visitIfExpression(expression: KtIfExpression) {
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
