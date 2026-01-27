package com.eygraber.detekt.rules.ktlint

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

public class EygraberRuleSetProvider : RuleSetProviderV3(
  id = RuleSetId(com.eygraber.detekt.rules.ktlint.RuleSetId.VALUE),
) {
  override fun getRuleProviders(): Set<RuleProvider> = setOf(
    RuleProvider { NoWhitespaceAfterKeyword() },
    RuleProvider { NewlineForMultilineKeyword() },
  )
}
