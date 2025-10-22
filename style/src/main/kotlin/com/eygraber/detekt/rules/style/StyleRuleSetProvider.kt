package com.eygraber.detekt.rules.style

import dev.detekt.api.RuleName
import dev.detekt.api.RuleSet
import dev.detekt.api.RuleSetProvider

public class StyleRuleSetProvider : RuleSetProvider {
  override val ruleSetId: RuleSet.Id = RuleSet.Id("style-eygraber")

  override fun instance(): RuleSet = RuleSet(
    id = ruleSetId,
    rules = mapOf(
      RuleName("NewlineForMultilineKeyword") to { config ->
        NewlineForMultilineKeyword(config)
      },
    ),
  )
}
