package com.example.basket4all

import com.google.firebase.auth.FirebaseUser

interface AuthServices {
    suspend fun createAccount(email: String, password: String): Result<FirebaseUser?>
}