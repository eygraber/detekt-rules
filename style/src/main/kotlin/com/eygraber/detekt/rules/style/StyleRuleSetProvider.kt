package com.eygraber.detekt.rules.style

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

public class StyleRuleSetProvider : RuleSetProvider {
  override val ruleSetId: String = "style-eygraber"

  override fun instance(config: Config): RuleSet = RuleSet(
    ruleSetId,
    listOf(NewlineForMultilineKeyword(config)),
  )
}
