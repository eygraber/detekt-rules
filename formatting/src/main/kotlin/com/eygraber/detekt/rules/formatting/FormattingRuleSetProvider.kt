package com.eygraber.detekt.rules.formatting

import dev.detekt.api.RuleName
import dev.detekt.api.RuleSet
import dev.detekt.api.RuleSetId
import dev.detekt.api.RuleSetProvider

public class FormattingRuleSetProvider : RuleSetProvider {
  override val ruleSetId: RuleSetId = RuleSetId("formatting-eygraber")

  override fun instance(): RuleSet = RuleSet(
    id = ruleSetId,
    rules = mapOf(
      RuleName("NoWhitespaceAfterKeyword") to { config ->
        NoWhitespaceAfterKeyword(config)
      },
    ),
  )
}
