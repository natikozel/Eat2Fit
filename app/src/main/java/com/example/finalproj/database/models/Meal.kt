package com.example.finalproj.database.models

data class Meal(
    val recipeUri: String? = null,
    val label: String? = null,
    val calories: Float? = null,
    var imageUri: String? = null
)