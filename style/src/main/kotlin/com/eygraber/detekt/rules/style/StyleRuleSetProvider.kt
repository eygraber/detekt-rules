package com.eygraber.detekt.rules.style

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class StyleRuleSetProvider : RuleSetProvider {
  override val ruleSetId = "style-eygraber"

  override fun instance(config: Config) = RuleSet(
    ruleSetId,
    listOf(NewlineForMultilineKeyword(config))
  )
}
