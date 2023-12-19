package app.books.tanga.codechecks

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

class PreviewAnnotationRule(config: Config) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "Compose functions annotated with @Preview must also be annotated with @ExcludeFromJacocoGeneratedReport",
        Debt.FIVE_MINS
    )

    override fun visitAnnotationEntry(annotationEntry: KtAnnotationEntry) {
        super.visitAnnotationEntry(annotationEntry)

        if (annotationEntry.shortName?.asString() != "Preview") return

        val owner = annotationEntry.getStrictParentOfType<KtFunction>() ?: return
        if (owner.annotationEntries.none { it.shortName?.asString() == "ExcludeFromJacocoGeneratedReport" }) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(annotationEntry),
                    message = "The @Preview function `${owner.name}` should also be " +
                        "annotated with @ExcludeFromJacocoGeneratedReport"
                )
            )
        }
    }
}
