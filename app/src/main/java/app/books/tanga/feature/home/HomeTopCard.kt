package app.books.tanga.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import app.books.tanga.common.FakeData
import app.books.tanga.feature.summary.SummaryImage
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.R
import app.books.tanga.feature.category.CategoryTag
import app.books.tanga.ui.theme.*

@Composable
fun HomeTopCard(summaryUi: SummaryUi) {
    val gradientColors = listOf(TangaBlueDark, TangaBluePale, TangaLightBlue)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clickable { },
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        backgroundColor = TangaBottomBarIconColorSelected40PercentTransparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(colors = gradientColors)
                )
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
            ) {
                CategoryTag(
                    icon = R.drawable.ic_indicator_mindmap,
                    categoryName = "Business"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Summary Of \nThe Day!",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h4.copy(
                        lineHeight = 30.sp
                    ),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Color.White,
                        text = "Check It Out Now",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = R.drawable.ic_right_arrow),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            SummaryImage(
                modifier = Modifier.width(90.dp),
                summaryCover = summaryUi.cover
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTopCardPreview() {
    HomeTopCard(summaryUi = FakeData.allSummaries().first())
}