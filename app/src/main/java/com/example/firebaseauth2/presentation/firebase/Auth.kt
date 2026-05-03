package com.example.firebaseauth2.presentation.firebase

import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI

enum class ProviderType {
    GOOGLE,
    GITHUB,
}

fun getSignInIntent(provider: ProviderType): Intent {
    val idpConfig = when (provider) {
        ProviderType.GOOGLE -> AuthUI.IdpConfig.GoogleBuilder().build()
        ProviderType.GITHUB -> AuthUI.IdpConfig.GitHubBuilder().build()
    }

    return AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(listOf(idpConfig))
        .build()
}

fun signOut(context: Context, onComplete: () -> Unit) {
    AuthUI.getInstance()
        .signOut(context)
        .addOnCompleteListener {
            onComplete()
        }
}
