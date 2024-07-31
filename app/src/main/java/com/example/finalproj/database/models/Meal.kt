package com.example.finalproj.database.models

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
enum class MealCategory {
    BREAKFAST, LUNCH, DINNER
}
data class Meal(
    val name: String? = null,
    val category: MealCategory? = null,
    val calories: Int? = null,
    val ingredients: List<Ingredient>? = null,
    val pictureUrl: String? = null,
    val timestamp: Long? = null // Unix timestamp for when the meal was eaten
) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

