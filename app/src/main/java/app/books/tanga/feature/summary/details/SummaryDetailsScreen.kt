package app.books.tanga.feature.summary.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.core_ui.components.SummaryActionButton
import app.books.tanga.core_ui.components.SummaryImage
import app.books.tanga.core_ui.components.TangaButtonLeftIcon
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.core_ui.theme.LocalSpacing
import app.books.tanga.core_ui.theme.LocalTintColor
import app.books.tanga.data.FakeData
import app.books.tanga.feature.summary.list.SummaryRow

@Composable
fun SummaryDetailsScreen(
    onBackClicked: () -> Unit,
    onPlayClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SummaryTopAppBar(onBackClicked)
        },
        floatingActionButton = { PlayFloatingActionButton(onClick = onPlayClicked) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SummaryDetailsHeader(Modifier.padding(it))
            Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))
            SummaryIntroduction()
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
            PurchaseButton()
            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
            Recommendations()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SummaryTopAppBar(onBackClicked: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { onBackClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = TangaIcons.LeftArrow),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "back navigation"
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_save),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "save summary"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_share_2),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "share summary"
                )
            }
        }
    )
}

@Composable
fun PlayFloatingActionButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.tertiary,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(18.dp),
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = app.books.tanga.core_ui.R.drawable.ic_indicator_listen),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "play summary"
        )
    }
}

@Composable
fun SummaryDetailsHeader(modifier: Modifier) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp),
        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = modifier.padding(LocalSpacing.current.medium),
                horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.large)
            ) {
                SummaryImage(
                    modifier = Modifier.width(128.dp),
                    painter = painterResource(id = R.drawable.cover_ego_is_enemy)
                ) {}

                SummaryBasicInfo()
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryActionButton(text = "Read", icon = TangaIcons.IndicatorRead) {}
                SummaryActionButton(text = "Listen", icon = TangaIcons.IndicatorListen) {}
                SummaryActionButton(text = "Watch", icon = TangaIcons.IndicatorWatch) {}
                SummaryActionButton(text = "Visualize", icon = TangaIcons.IndicatorGraphic) {}
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        }
    }
}

@Composable
private fun SummaryBasicInfo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Ego is the Enemy",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline,
            text = "Ryan Holliday",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = TangaIcons.IndicatorListen),
                contentDescription = null,
                tint = LocalTintColor.current.color
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.summary_duration, "10"),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun SummaryIntroduction(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = LocalSpacing.current.medium)) {
        Text(
            text = stringResource(id = R.string.summary_details_introduction),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline,
            text = "Ego Is the Enemy puts forth the argument that often our biggest problems are not caused by external factors such as other people or circumstances. Instead, our problems stem from our own attitude, selfishness and self-absorption. In other words, introducing ego into a situation often prevents us from being rational, objective and clear headed.[9] The book does not discuss Freud's ego or egotism as a clinical term but rather ego in a colloquial sense, defined as \"an unhealthy belief in your own importance.\"[10] The book also discusses the difference between ego and confidence, and argues that the solution to the problem of ego is humility, self-awareness, purpose and realism.[5][11][12] Ego Is the Enemy provides both cautionary tales as well as positive anecdotes about ego, citing numerous historical and contemporary figures including Christopher McCandless, George Marshall, John DeLorean, Larry Page, Paul Graham, Steve Jobs and William Tecumseh Sherman",
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun PurchaseButton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.medium)
    ) {
        TangaButtonLeftIcon(
            text = "Purchase Book",
            rightIcon = app.books.tanga.core_ui.R.drawable.ic_trolley,
            onClick = { /*TODO*/ }
        )
    }
}


@Composable
fun Recommendations(modifier: Modifier = Modifier) {
    val recommendedSummaries = remember {
        FakeData.allSummaries().shuffled().take(4)
    }
    Column(modifier = modifier.padding(LocalSpacing.current.medium)) {
        Text(
            text = stringResource(id = R.string.summary_details_recommendations),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        SummaryRow(
            summaries = recommendedSummaries
        ) { /*TODO*/ }
    }
}

@Preview
@Composable
fun SummaryHeaderPreview() {
    SummaryDetailsHeader(modifier = Modifier)
}