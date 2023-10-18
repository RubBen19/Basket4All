package com.example.basket4all

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseAuthService (private var auth: FirebaseAuth) : AuthServices {

    companion object {
        @Volatile
        private var instance: FirebaseAuthService? = null

        fun getInstance(auth: FirebaseAuth): FirebaseAuthService {
            return instance ?: synchronized(this) {
                instance ?: FirebaseAuthService(auth).also { instance = it }
            }
        }
    }

    override suspend fun createAccount(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}