package com.example.finalproj.database


import com.example.finalproj.database.models.Gender
import com.example.finalproj.database.models.Goal
import com.example.finalproj.database.models.Meal
import com.example.finalproj.database.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthenticationManager {
    private val auth: FirebaseAuth = Firebase.auth
    private lateinit var currentUser: User

    fun setUser(user: User) {
        currentUser = user
    }
    fun getUser(): User {
        return currentUser
    }
//
//    fun getCalories(): Int? {
//        return currentUser.maxCalories
//    }
//
//    fun getGender(): Gender? {
//        return currentUser.gender
//    }
//
//    fun getGoal(): Goal? {
//        return currentUser.goal
//    }
//
//    fun getWeight(): Double? {
//        return currentUser.weight
//    }
//
//    fun getHeight(): Double? {
//        return currentUser.height
//    }
//
//    fun getAge(): Int? {
//        return currentUser.age
//    }
//
//    fun getFullName(): String? {
//        return currentUser.fullName
//    }
//
//    fun getImageUrl(): String? {
//        return currentUser.imageUrl
//    }
//
//    fun getRecentMeal(): Meal? {
//        return currentUser.recentMeal
//    }
//
//    fun getSuggestedMeals(): HashMap<String, Meal>? {
//        return currentUser.suggestedMeals
//    }
//
//    fun getPreviousWeights(): List<Double>? {
//        return currentUser.previousWeights
//    }
//
//    fun getPreviousMeals(): HashMap<String, Meal>? {
//        return currentUser.previousMeals
//    }
//
//    fun getHasLoggedInOnce(): Boolean? {
//        return currentUser.hasLoggedInOnce
//    }


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