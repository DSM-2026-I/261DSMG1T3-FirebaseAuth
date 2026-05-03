package com.example.firebaseauth2

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firebaseauth2.presentation.firebase.signOut
import com.example.firebaseauth2.presentation.home.HomeScreen
import com.example.firebaseauth2.presentation.initial.InitialScreen
import com.example.firebaseauth2.presentation.login.LoginScreen
import com.example.firebaseauth2.presentation.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
){
    NavHost(
        navController = navHostController,
        startDestination = "initial"
    ){
        composable ("initial"){
            InitialScreen(
                navigateToLogin = {navHostController.navigate("login")},
                navigateToSignUp = {navHostController.navigate("signup")},
                onLoginSuccess = {
                    navHostController.navigate("home"){
                        popUpTo("initial") {inclusive = true}
                    }
                }
            )
        }
        composable ("login"){
            LoginScreen(
                auth,
                navigateToSignUp = {navHostController.navigate("signup")},
                onLoginSuccess = {
                    navHostController.navigate("home"){
                        popUpTo("initial") {inclusive = true}
                    }
                }
            )
        }
        composable ("signup"){
            SignUpScreen(
                auth,
                onRegisterSuccess = { navHostController.popBackStack()}
            )
        }
        composable ("home"){
            val context = LocalContext.current
            HomeScreen(auth.currentUser?.email) {
                signOut(context) {
                    navHostController.navigate("initial") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }
        }
    }
}