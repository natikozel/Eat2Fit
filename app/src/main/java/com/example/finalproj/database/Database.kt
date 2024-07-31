package com.example.finalproj.database


import com.example.finalproj.database.models.Meal
import com.example.finalproj.database.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


// DatabaseKeys Enum
enum class DatabaseKeys(val key: String) {
    USERS("users"),
    MEALS("meals"),
    WEIGHT("weight"),
    PREVIOUS_WEIGHTS("previousWeights"),
    CALORIES("calories"),
    FULL_NAME("fullName"),
    EMAIL("email"),
    PASSWORD("password"),
    GENDER("gender"),
    AGE("age"),
    GOAL("goal"),
    HEIGHT("height"),
    HAS_LOGGED_IN_ONCE("hasLoggedInOnce")
}


object DatabaseManager {
    private lateinit var database: DatabaseReference

    fun initDb() {
        Firebase.database.setPersistenceEnabled(true)
        database = Firebase.database.reference
    }

    fun writeMeal(meal: Meal) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        val mealId =
            database.child(DatabaseKeys.USERS.key).child(userId).child(DatabaseKeys.MEALS.key)
                .push().key
        if (mealId != null) {
            database.child(DatabaseKeys.USERS.key).child(userId).child(DatabaseKeys.MEALS.key)
                .child(mealId).setValue(meal)
        }
    }

    fun readMeals(onSuccess: (List<Meal>) -> Unit, onFailure: (Exception) -> Unit) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        database.child(DatabaseKeys.USERS.key).child(userId).child(DatabaseKeys.MEALS.key).get()
            .addOnSuccessListener { dataSnapshot ->
                val meals = dataSnapshot.children.mapNotNull { it.getValue(Meal::class.java) }
                onSuccess(meals)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun updateUserWeight(newWeight: Double) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        database.child(DatabaseKeys.USERS.key).child(userId).child(DatabaseKeys.WEIGHT.key)
            .setValue(newWeight)
    }

    fun addPreviousWeight(weight: Double) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        database.child(DatabaseKeys.USERS.key).child(userId)
            .child(DatabaseKeys.PREVIOUS_WEIGHTS.key).push().setValue(weight)
    }

    private fun updateUserCalories(userId: String, calories: Int) {
        database.child(DatabaseKeys.USERS.key).child(userId).child(DatabaseKeys.CALORIES.key).get()
            .addOnSuccessListener { dataSnapshot ->
                val currentCalories = dataSnapshot.getValue(Int::class.java) ?: 0
                database.child(DatabaseKeys.USERS.key).child(userId)
                    .child(DatabaseKeys.CALORIES.key).setValue(currentCalories + calories)
            }
    }

    fun addUserListener(onDataChange: (User?) -> Unit, onError: (DatabaseError) -> Unit) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        val userRef = database.child(DatabaseKeys.USERS.key).child(userId)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                onDataChange(user)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }

    fun addMealsListener(
        onChildAdded: (Meal) -> Unit,
        onChildChanged: (Meal) -> Unit,
        onChildRemoved: (Meal) -> Unit,
        onError: (DatabaseError) -> Unit
    ) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        val mealsRef =
            database.child(DatabaseKeys.USERS.key).child(userId).child(DatabaseKeys.MEALS.key)
        mealsRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val meal = snapshot.getValue(Meal::class.java)
                if (meal != null) {
                    onChildAdded(meal)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val meal = snapshot.getValue(Meal::class.java)
                if (meal != null) {
                    onChildChanged(meal)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val meal = snapshot.getValue(Meal::class.java)
                if (meal != null) {
                    onChildRemoved(meal)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }

    fun updateUser(user: User) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        database.child(DatabaseKeys.USERS.key).child(userId).setValue(user)
    }

    fun updateUserKey(key: DatabaseKeys, value: Any) {
        val userId = AuthenticationManager.getCurrentUser()?.uid ?: return
        database.child(DatabaseKeys.USERS.key).child(userId).child(key.key).setValue(value)
    }

    fun readUserKey(key: DatabaseKeys): Task<DataSnapshot>? {
        val userId = AuthenticationManager.getCurrentUser()?.uid
        userId?.let {
            return database.child(DatabaseKeys.USERS.key).child(userId)
                .child(key.key).get()
        } ?: return null
    }

    fun readUser(userId: String): Task<DataSnapshot> {
        return database.child(DatabaseKeys.USERS.key).child(userId).get()
    }

    fun readUser(userId: String, onSuccess: (User?) -> Unit, onFailure: (Exception) -> Unit) {
        database.child(DatabaseKeys.USERS.key).child(userId).get()
            .addOnSuccessListener { dataSnapshot ->
                val user = dataSnapshot.getValue(User::class.java)
                onSuccess(user)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}