package com.example.finalproj.database.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties


enum class Goal {
    GAIN, LOSE, MAINTAIN
}

enum class Gender {
    MALE, FEMALE
}

data class User(
    var email: String? = null,
    var password: String? = null,
    var fullName: String? = null,
    var gender: Gender? = null,
    var age: Int? = null,
    var goal: Goal? = null,
    var height: Double? = null,
    var weight: Double? = null,
    var previousWeights: List<Double>? = null,
    var meals: List<Meal>? = null, // List of meals eaten by the user
    var hasLoggedInOnce: Boolean?= false,
//    var imageUrl: String? = null // New property for image URL


) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}



