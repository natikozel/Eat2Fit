package com.example.finalproj.database.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Ingredient(
    val name: String,
    val quantity: String // e.g., "2 cups", "200 grams"
) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}