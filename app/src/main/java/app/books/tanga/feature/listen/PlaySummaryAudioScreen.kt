package app.books.tanga.feature.listen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.books.tanga.core_ui.components.SummaryImage
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.core_ui.theme.LocalSpacing
import app.books.tanga.data.FakeData
import app.books.tanga.feature.summary.list.SummaryUi

@Composable
fun PlaySummaryAudioScreen(onBackClicked: () -> Unit) {
    val summary = remember {
        FakeData.allSummaries().shuffled().last()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = LocalSpacing.current.large),
        topBar = {
            PlaySummaryAudioTopBar(onBackClicked)
        },
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PlaySummaryAudioContent(summary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySummaryAudioTopBar(onBackClicked: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = TangaIcons.LeftArrow),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "back navigation"
                )
            }
        }
    )
}

@Composable
fun PlaySummaryAudioContent(summary: SummaryUi) {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 148.dp),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))
                Spacer(modifier = Modifier.height(LocalSpacing.current.large))

                Box(
                    modifier = Modifier
                        .width(34.dp)
                        .height(4.dp)
                        .background(MaterialTheme.colorScheme.onTertiaryContainer)
                )

                Spacer(modifier = Modifier.height(LocalSpacing.current.large))

                Text(
                    text = "Ego is the Enemy",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(modifier = Modifier.height(LocalSpacing.current.small))

                Text(
                    color = MaterialTheme.colorScheme.outline,
                    text = "Ryan Holliday",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = TangaIcons.Backward),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "previous"
                        )
                    }
                    Text(
                        color = MaterialTheme.colorScheme.outline,
                        text = "-15s",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(64.dp)) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(14.dp),
                                painter = painterResource(id = TangaIcons.Pause),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "play"
                            )
                        }
                    }
                    Text(
                        color = MaterialTheme.colorScheme.outline,
                        text = "+15s",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = TangaIcons.Forward),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "next"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))
                var sliderPosition by remember { mutableFloatStateOf(0.5f) }
                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),

                    value = sliderPosition, onValueChange = { sliderPosition = it })
            }
        }
        SummaryImage(
            modifier = Modifier
                .width(154.dp)
                .align(alignment = Alignment.TopCenter),
            painter = painterResource(id = summary.cover),
            onSummaryClicked = { }
        )
    }
}
