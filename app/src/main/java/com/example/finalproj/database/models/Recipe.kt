package com.example.finalproj.database.models

enum class ImageType {
    THUMBNAIL, REGULAR, LARGE
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