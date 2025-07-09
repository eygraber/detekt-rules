package com.eygraber.detekt.rules.formatting

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

public class FormattingRuleSetProvider : RuleSetProvider {
  override val ruleSetId: String = "formatting-eygraber"

  override fun instance(config: Config): RuleSet = RuleSet(
    ruleSetId,
    listOf(NoWhitespaceAfterKeyword(config)),
  )
}
