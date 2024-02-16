package app.books.tanga.feature.read

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TextScaleFactorTest {

    @Test
    fun `toScaleFactor returns correct scale factor for given float`() {
        assertEquals(TextScaleFactor.EXTRA_SMALL, 0.0f.toScaleFactor())
        assertEquals(TextScaleFactor.SMALL, 0.1f.toScaleFactor())
        assertEquals(TextScaleFactor.LARGE, 0.5f.toScaleFactor())
        assertEquals(TextScaleFactor.EXTRA_LARGE, 0.7f.toScaleFactor())
        assertEquals(TextScaleFactor.ACCESSIBILITY_SIZE, 0.9f.toScaleFactor())
    }

    @Test
    fun `toScaleFactor handles edge cases`() {
        assertEquals(TextScaleFactor.EXTRA_SMALL, (-0.1f).toScaleFactor())
        assertEquals(TextScaleFactor.ACCESSIBILITY_SIZE, 1.1f.toScaleFactor())
    }
}
