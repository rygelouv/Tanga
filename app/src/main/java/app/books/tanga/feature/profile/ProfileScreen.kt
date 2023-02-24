package app.books.tanga.feature.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.books.tanga.R
import app.books.tanga.ui.theme.TangaBlueDark
import app.books.tanga.ui.theme.TangaBluePaleSemiTransparent
import app.books.tanga.ui.theme.TangaBluePaleSemiTransparent2
import app.books.tanga.ui.theme.TangaLightGray2
import app.books.tanga.ui.theme.TangaBluePale

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize().padding(bottom = 10.dp),
        floatingActionButton = {
            ProButton()
        },
        scaffoldState = rememberScaffoldState(),
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Surface(
            color = TangaBluePaleSemiTransparent2,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                ProfileHeader(modifier = Modifier)
                Spacer(modifier = Modifier.height(70.dp))
                ProfileScreenContent()
            }
        }
    }
}

@Composable
fun ProfileScreenContent() {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            ProfileContentAction(modifier = Modifier, ProfileAction.CONTACT)
            ProfileContentAction(modifier = Modifier, ProfileAction.PRIVACY_AND_TERMS)
            ProfileContentAction(modifier = Modifier, ProfileAction.NOTIFICATIONS)
            ProfileContentAction(modifier = Modifier, ProfileAction.LOGOUT)
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun ProfileContentAction(modifier: Modifier, action: ProfileAction) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = TangaBluePaleSemiTransparent2),
            ) {}
            .padding(horizontal = 30.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = action.iconBackgroundColor)
                .padding(all = 10.dp)
        ) {
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = ImageVector.vectorResource(id = action.icon),
                contentDescription = "action icon",
                tint = action.color
            )
        }
        Spacer(modifier = Modifier.width(25.dp))
        Text(
            modifier = Modifier.weight(5f),
            text = stringResource(id = action.text),
            style = MaterialTheme.typography.h4,
            color = if (action.shouldTint) action.color else TangaBlueDark,
            fontSize = 14.sp
        )
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevon_right),
            contentDescription = "action icon",
            tint = if (action.shouldTint) action.color else TangaLightGray2
        )
    }
}

@Composable
fun ProfileHeader(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        ProfileImage(modifier = Modifier)

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Stephanie Milton",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            color = TangaBlueDark,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier.height(34.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = TangaBluePaleSemiTransparent,
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Freemium",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ProButton() {
    Box(modifier = Modifier
        .fillMaxSize()) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .height(70.dp)
                .width(320.dp).align(Alignment.BottomCenter),
            backgroundColor = TangaBluePale,
            onClick = {},
            elevation = FloatingActionButtonDefaults.elevation(2.dp),
            contentColor = Color.Unspecified,
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Upgrade to Tanga Pro",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            icon = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .padding(all = 10.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_premium),
                        contentDescription = "action icon",
                    )
                }
            }
        )
    }
}

@Composable
fun ProfileImage(modifier: Modifier) {
    Surface(
        modifier = modifier.size(120.dp),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.jessica_felicio_image),
            contentDescription = "profile picture",
            modifier = modifier.size(120.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}