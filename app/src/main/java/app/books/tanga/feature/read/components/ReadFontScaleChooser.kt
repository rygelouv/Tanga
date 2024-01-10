package app.books.tanga.feature.read.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.R
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme

@Composable
fun ReadFontScaleChooser(
    initialValue: Float,
    modifier: Modifier = Modifier,
    onFontScaleChange: (Float) -> Unit
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                .padding(LocalSpacing.current.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .testTag("font_size_min"),
                painter = painterResource(id = R.drawable.font_size),
                tint = Color.White,
                contentDescription = "adjust font size"
            )
            Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
            ScaleFactorSliderPicker(
                modifier = Modifier.weight(1f),
                selectedScaleFactorProgressValue = initialValue,
                onScaleFactorChange = onFontScaleChange
            )
            Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
            Icon(
                modifier = Modifier
                    .size(26.dp)
                    .testTag("font_size_min"),
                painter = painterResource(id = R.drawable.font_size),
                tint = Color.White,
                contentDescription = "adjust font size"
            )
        }
        Spacer(modifier = Modifier.height(0.5.dp).background(MaterialTheme.colorScheme.secondary))
    }
}

@Composable
fun ScaleFactorSliderPicker(
    selectedScaleFactorProgressValue: Float,
    modifier: Modifier = Modifier,
    onScaleFactorChange: (Float) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(selectedScaleFactorProgressValue) }
    Slider(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp),
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
            onScaleFactorChange(it)
        },
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.tertiary,
            activeTrackColor = MaterialTheme.colorScheme.tertiary,
            inactiveTrackColor = MaterialTheme.colorScheme.secondary
        ),
    )
}

@Preview
@Composable
private fun ReadFontScaleChooserPreview() {
    TangaTheme {
        ReadFontScaleChooser(
            onFontScaleChange = {},
            initialValue = 1.0f
        )
    }
}
