package com.example.finalproj.database


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthenticationManager {
    private val auth: FirebaseAuth = Firebase.auth

    fun registerUser(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        onSuccess(user)
                    } else {
                        onFailure(Exception("User registration failed"))
                    }
                } else {
                    onFailure(task.exception ?: Exception("User registration failed"))
                }
            }
    }

    fun getAuth(): FirebaseAuth {
        return auth
    }

    fun loginUser(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        onSuccess(user)
                    } else {
                        onFailure(Exception("User login failed"))
                    }
                } else {
                    onFailure(task.exception ?: Exception("User login failed"))
                }
            }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logoutUser() {
        auth.signOut()
    }
}