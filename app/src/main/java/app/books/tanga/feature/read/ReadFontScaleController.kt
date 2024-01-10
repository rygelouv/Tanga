package app.books.tanga.feature.read

enum class TextScaleFactor(val value: Float) {
    EXTRA_SMALL(0.75f),
    SMALL(0.875f),
    DEFAULT(1.0f),
    LARGE(1.125f),
    EXTRA_LARGE(1.25f),
    ACCESSIBILITY_SIZE(1.5f);

    companion object {
        fun range(): ClosedFloatingPointRange<Float> = EXTRA_SMALL.value..ACCESSIBILITY_SIZE.value
    }
}

fun Float.toScaleFactor(): TextScaleFactor {
    val minScaleFactor = TextScaleFactor.entries.toTypedArray().minOf { it.value }
    val maxScaleFactor = TextScaleFactor.entries.toTypedArray().maxOf { it.value }

    // Normalize the progress to the range of scale factors
    val normalizedValue = this * (maxScaleFactor - minScaleFactor) + minScaleFactor

    // Find the closest scale factor to the normalized value
    return TextScaleFactor.entries.toTypedArray().minByOrNull { kotlin.math.abs(it.value - normalizedValue) }
        ?: TextScaleFactor.DEFAULT
}

fun TextScaleFactor.toProgressFloat(): Float {
    // Assuming the smallest scale factor maps to 0f and the largest to 1f
    val minScaleFactor = TextScaleFactor.entries.toTypedArray().minOf { it.value }
    val maxScaleFactor = TextScaleFactor.entries.toTypedArray().maxOf { it.value }

    return (this.value - minScaleFactor) / (maxScaleFactor - minScaleFactor)
}
