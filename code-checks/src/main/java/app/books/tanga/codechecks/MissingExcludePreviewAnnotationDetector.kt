package app.books.tanga.codechecks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.TextFormat
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod

/**
 * A custom lint detector that checks for missing `@ExcludeFromJacocoGeneratedReport`
 * annotations on Jetpack Compose preview methods.
 *
 * This detector scans through method declarations in Kotlin files and reports any `@Preview` annotated methods
 * that are not also annotated with `@ExcludeFromJacocoGeneratedReport`. This ensures that preview methods
 * are properly excluded from JaCoCo coverage reports.
 *
 * Taken from here: https://github.com/AdamMc331/TOA/blob/96248ce1e8c27817779d4785a2f73d4100a1ea90/lint-checks/src/main/java/com/adammcneilly/toa/lint/MissingExcludePreviewAnnotationDetector.kt
 */
@Suppress("UnstableApiUsage")
class MissingExcludePreviewAnnotationDetector : Detector(), Detector.UastScanner {

    /**
     * Specifies that this detector is interested in method declarations.
     */
    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(UMethod::class.java)

    /**
     * Creates a handler for processing UAST method nodes.
     */
    override fun createUastHandler(context: JavaContext): UElementHandler = PreviewMethodElementHandler(context)

    /**
     * An inner class to handle method nodes detected by the detector.
     */
    private class PreviewMethodElementHandler(private val context: JavaContext) : UElementHandler() {

        /**
         * Visits each method node in the UAST.
         *
         * Checks if the method is a Compose preview method and whether it's properly
         * annotated to be excluded from JaCoCo reports.
         */
        override fun visitMethod(node: UMethod) {
            val isComposePreviewMethod = node.hasAnnotation(COMPOSE_PREVIEW_ANNOTATION)
            val isExcludedFromJacoco = node.hasAnnotation(EXCLUDE_FROM_JACOCO_ANNOTATION)

            if (isComposePreviewMethod && isExcludedFromJacoco.not()) {
                context.report(
                    issue = ISSUE_MISSING_EXCLUDE_PREVIEW_ANNOTATION,
                    location = context.getLocation(node),
                    message = ISSUE_MISSING_EXCLUDE_PREVIEW_ANNOTATION.getExplanation(TextFormat.TEXT),
                )
            }
        }
    }

    companion object {
        private const val COMPOSE_PREVIEW_ANNOTATION = "androidx.compose.ui.tooling.preview.Preview"
        private const val EXCLUDE_FROM_JACOCO_ANNOTATION =
            "app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport"

        /**
         * Definition of the lint issue checked by this detector.
         */
        @Suppress("MaxLineLength")
        internal val ISSUE_MISSING_EXCLUDE_PREVIEW_ANNOTATION = Issue.create(
            id = "MissingExcludePreviewAnnotation",
            briefDescription = "Jetpack Compose previews should be excluded from JaCoCo Reports.",
            explanation = "Any methods annotated with @Preview should also have " +
                "the @ExcludeFromJacocoGeneratedReport annotation.",
            category = Category.CUSTOM_LINT_CHECKS,
            severity = Severity.ERROR,
            implementation = Implementation(
                MissingExcludePreviewAnnotationDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
            priority = 10,
        )
    }
}
