package app.books.tanga.coreui.common

/**
 * An annotation we can added to compose preview functions so that they are ignored by jacoco coverage reports
 * https://stackoverflow.com/questions/47824761/how-would-i-add-an-annotation-to-exclude-a-method-from-a-jacoco-code-coverage-re/66918619#66918619
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class ExcludeFromJacocoGeneratedReport
