package app.books.tanga.codechecks.provider

import app.books.tanga.codechecks.PreviewAnnotationRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

/**
 * Detekt Custom rule provider.
 * We use this provide to introduce our own Detekt rules
 * See: https://detekt.dev/docs/introduction/extensions/
 */
class RuleProvider : RuleSetProvider {

    override val ruleSetId: String = "tanga-custom-rules"

    override fun instance(config: Config): RuleSet = RuleSet(ruleSetId, listOf(PreviewAnnotationRule(config)))
}
