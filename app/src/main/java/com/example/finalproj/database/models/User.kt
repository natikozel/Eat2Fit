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
    var fullName: String? = null,
    var gender: Gender? = null,
    var age: Int? = null,
    var goal: Goal? = null,
    var height: Double? = null,
    var weight: Double? = null,
    var maxCalories: Int? = null,
    var previousWeights: List<Double>? = null,
    var previousMeals: HashMap<String, Meal> = HashMap(),
    var hasLoggedInOnce: Boolean? = false,
    var imageUrl: String? = null,
    var recentMeal: Meal? = null,
    var suggestedMeals: List<Meal>? = null,
)



