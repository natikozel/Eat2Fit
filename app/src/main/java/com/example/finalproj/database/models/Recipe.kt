package com.example.finalproj.database.models

import com.google.firebase.database.IgnoreExtraProperties

enum class ImageType {
    THUMBNAIL, REGULAR
}

data class Recipe(
    val recipeUri: String? = null,
    val label: String? = null,
    val imageUris: List<Pair<ImageType, String>>? = null,
    val ingredientLines: List<String>? = null,
    val ingredients: List<Ingredient>? = null,
    val calories: Float? = null,
    val totalWeight: Float? = null,
    val mealType: String? = null
)