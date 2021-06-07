package com.eygraber.detekt.rules.formatting

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class FormattingRuleSetProvider : RuleSetProvider {
  override val ruleSetId = "formatting-eygraber"

  override fun instance(config: Config) = RuleSet(
    ruleSetId,
    listOf(NoWhitespaceAfterKeyword(config))
  )
}
