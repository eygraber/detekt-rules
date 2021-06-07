package com.eygraber.detekt.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class NoWhitespaceAfterKeywordRuleSetProvider : RuleSetProvider {
  override val ruleSetId = "eygraber"

  override fun instance(config: Config) = RuleSet(
    ruleSetId,
    listOf(NoWhitespaceAfterKeyword(config))
  )
}
