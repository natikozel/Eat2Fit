package com.example.finalproj.database


import com.example.finalproj.database.models.Gender
import com.example.finalproj.database.models.Meal
import com.example.finalproj.database.models.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.Locale


// DatabaseKeys Enum
enum class DatabaseKeys(val key: String) {
    USERS("users"),
    PREVIOUS_MEALS("previousMeals"),
    WEIGHT("weight"),
    SUGGESTED_MEALS("suggestedMeals"),
    PREVIOUS_WEIGHTS("previousWeights"),
    CALORIES("maxCalories"),
    RECENT_MEAL("recentMeal"),
    FULL_NAME("fullName"),
    EMAIL("email"),
    PASSWORD("password"),
    GENDER("gender"),
    AGE("age"),
    GOAL("goal"),
    HEIGHT("height"),
    HAS_LOGGED_IN_ONCE("hasLoggedInOnce"),
    IMAGE_URL("imageUrl")
}


object DatabaseManager {
    private lateinit var database: DatabaseReference

    fun initDb() {
        Firebase.database.setPersistenceEnabled(true)
        database = Firebase.database.reference
    }


    fun attachUserListener() {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        database.child(DatabaseKeys.USERS.key).child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    AuthenticationManager.setUser(dataSnapshot.getValue(User::class.java)!!)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                }
            })
    }


    fun updateRecentlyWatchedMeal(meal: Meal) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        database.child(DatabaseKeys.USERS.key).child(userId).child(DatabaseKeys.RECENT_MEAL.key)
            .setValue(meal)

    }


    fun writeMeal(meal: Meal) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        val mealsRef =
            database.child(DatabaseKeys.USERS.key).child(userId)
                .child(DatabaseKeys.PREVIOUS_MEALS.key)
        val newMealKey = mealsRef.push().key ?: return
        mealsRef.child(newMealKey).setValue(meal)

    }

    fun deleteMeal(mealId: String): Task<Void> {
        val userId = AuthenticationManager.getCurrentUser()?.uid
        val res = database.child(DatabaseKeys.USERS.key).child(userId!!)
            .child(DatabaseKeys.PREVIOUS_MEALS.key)
            .child(mealId).removeValue()
        return res
    }


    fun updateUser(user: User) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        database.child(DatabaseKeys.USERS.key).child(userId).setValue(user)

    }

    fun updateUserKey(key: DatabaseKeys, value: Any? = null) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        database.child(DatabaseKeys.USERS.key).child(userId).child(key.key).setValue(value)

    }


    fun readUser(): Task<DataSnapshot> {
        return database.child(DatabaseKeys.USERS.key)
            .child(AuthenticationManager.getCurrentUser()?.uid!!).get()
    }


}