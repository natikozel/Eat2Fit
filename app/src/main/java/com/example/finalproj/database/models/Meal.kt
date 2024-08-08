package com.example.finalproj.database.models

import java.util.UUID

data class Meal(
    val recipeUri: String? = null,
    val label: String? = null,
    val calories: Float? = null,
    var imageUri: String? = null
)