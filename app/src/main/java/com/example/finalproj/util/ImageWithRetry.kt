package com.example.finalproj.util

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import coil.request.ImageRequest
import com.example.finalproj.R
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.SearchAPI
import com.example.finalproj.database.models.ImageType
import com.example.finalproj.database.models.Meal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun fetchRecentMeal(
    coroutineScope: CoroutineScope,
    meal: Meal?,
    loadingState: MutableState<Boolean>,
    isRecentMeal: Boolean? = false,
    isSuggestedMeal: Boolean? = false,
    index: String? = "",
) {
    coroutineScope.launch {
        loadingState.value = true
        val recipeUrl = meal?.recipeUri?.substringAfterLast("recipe_")
        val recipe = SearchAPI.getRecipe(recipeUrl!!)
        meal.imageUri =
            recipe?.imageUris?.firstOrNull { it.first === ImageType.REGULAR }?.second
        if (isRecentMeal!!) {
            DatabaseManager.updateRecentlyWatchedMeal(meal)
        } else if (isSuggestedMeal!!) {
            SearchAPI.findSuggestedMeals()
        } else {
            DatabaseManager.deleteMeal(index!!)
            DatabaseManager.writeMeal(meal)
        }
        loadingState.value = false
    }
}


fun loadImageWithRetry(
    context: Context,
    imageUri: String,
    onError: () -> Unit
): ImageRequest {
    return ImageRequest.Builder(context)
        .data(imageUri)
        .crossfade(true)
        .error(R.drawable.expired)
        .listener(
            onError = { _, _ -> onError() }
        )
        .build()
}