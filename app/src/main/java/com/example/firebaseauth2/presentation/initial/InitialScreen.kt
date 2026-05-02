package com.example.firebaseauth2.presentation.initial

import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firebaseauth2.presentation.firebase.ProviderType
import com.example.firebaseauth2.presentation.firebase.getSignInIntent
import com.example.firebaseauth2.ui.theme.FirebaseAuth2Theme
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.R

@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit = {},
    navigateToSignUp: () -> Unit = {},
    onLogInSuccess: () -> Unit = {}
){
    val context = LocalContext.current
    val signInLauncher = rememberLauncherForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        if (res.resultCode == RESULT_OK) {
            onLogInSuccess()
        } else {
            val response = res.idpResponse
            Toast.makeText(
                context,
                "Login failed!! ${response?.error?.message}",
                Toast.LENGTH_LONG
            ).show()
            Log.e("AUTH", "Error: ${response?.error?.message}")
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = navigateToSignUp,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Register with email")
        }

        Button(
            onClick = navigateToLogin,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login with email")
        }
        // GOOGLE
        SocialLoginButton(
            text = "Continue with Google",
            color = Color(0xFF59AAD0),
            iconRes = R.drawable.fui_ic_googleg_color_24dp,
            onClick = { signInLauncher.launch(getSignInIntent(ProviderType.GOOGLE)) }
        )
        // GITHUB
        SocialLoginButton(
            text = "Continue with Github",
            color = Color(0xFF333333),
            iconRes = R.drawable.fui_ic_github_white_24dp,
            onClick = { signInLauncher.launch(getSignInIntent(ProviderType.GITHUB)) }
        )
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    color: Color,
    iconRes: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InitialScreenPreview() {
    FirebaseAuth2Theme {
        InitialScreen(
            navigateToLogin = {},
            navigateToSignUp = {},
            onLogInSuccess = {}
        )
    }
}