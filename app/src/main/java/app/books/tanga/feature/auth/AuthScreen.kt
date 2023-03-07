package app.books.tanga.feature.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.books.tanga.R
import app.books.tanga.navigation.NavigationScreen
import app.books.tanga.ui.theme.TangaBlueDark
import app.books.tanga.ui.theme.TangaLightGray2
import app.books.tanga.ui.theme.TangaOrange
import app.books.tanga.ui.theme.TangaOrangeTransparent

@Composable
fun AuthScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(
            elevation = 0.dp,
            backgroundColor = Color.White
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.weight(8f))
                Button(
                    modifier = Modifier
                        .size(76.dp)
                        .weight(2f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = TangaOrange,
                        backgroundColor = TangaOrangeTransparent,
                    ),
                    shape = RoundedCornerShape(40.dp),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                    onClick = { navController.navigate(route = NavigationScreen.Main.route) }
                ) {
                    Text(
                        text = "Skip",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }) {
        AuthContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
fun AuthContent(modifier: Modifier, navController: NavController) {
    val state = rememberGoogleOneTapSignInState()
    GoogleOneTapSignIn(
        state = state,
        clientId = "806931145399-5o4s110s8qa6a7sbon8h3a52caq5pb22.apps.googleusercontent.com",
        nonce = "sama nonnce",
        onTokenIdReceived = {
            Log.d("MainActivity", "TOKEN ==> $it")
        },
        onOneTapDialogDismissed = {
            Log.d("MainActivity", "Error ==> $it")
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            painter = painterResource(id = R.drawable.graphic_pricing),
            contentDescription = "app icon"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Welcome to Tanga",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                color = TangaBlueDark,
                fontSize = 28.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 41.dp)
                    .padding(top = 10.dp)
                    .weight(1f),
                color = TangaLightGray2,
                text = "Sign in or Sign up with Google to start enjoying Tanga Now",
                fontSize = 14.sp,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
        Button(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            onClick = {
                // TODO: state.open()
                navController.navigate(route = NavigationScreen.Main.route)
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Transparent,
                backgroundColor = Color.Black,
            ),
            shape = RoundedCornerShape(6.dp),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp),
        ) {
            Box {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.google_logo),
                    contentDescription = "google logo",
                    modifier = Modifier.size(22.dp)
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Sign in with Google",
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 41.dp)
                .padding(top = 14.dp),
            color = TangaLightGray2,
            text = "Terms and Conditions",
            fontSize = 12.sp,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen(rememberNavController())
}