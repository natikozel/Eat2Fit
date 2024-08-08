package com.example.finalproj.database

import android.util.Log
import com.example.finalproj.database.models.ImageType
import com.example.finalproj.database.models.Ingredient
import com.example.finalproj.database.models.Meal
import com.example.finalproj.database.models.Recipe
import com.example.finalproj.views.leftoverCalories
import com.google.gson.JsonNull
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object SearchAPI {

    private const val appId = "a1dbb1d0"
    private const val appKey = "c5c0f81965655cc17774c63cb730588d"
    private const val user = "kozelnono"
    private const val auth = "Basic MGNkNWUzMGE6NTJhYmJkOGMzYmZlYmQzYzc4NDAxYzFmNWQ1ZGU4NjY="
    private const val baseUrl = "http%3A%2F%2Fwww.edamam.com%2Fontologies%2Fedamam.owl%23recipe_"


    private fun extractSuggestedRecipes(response: String): List<String?> {
        val jsonObject = JsonParser.parseString(response).asJsonObject
        val selection = jsonObject.getAsJsonArray("selection").get(0).asJsonObject
        val sections = selection.getAsJsonObject("sections")
        val breakfastAssigned = sections.getAsJsonObject("Breakfast").get("assigned")?.asString
        val lunchAssigned = sections.getAsJsonObject("Lunch").get("assigned")?.asString
        return listOf(breakfastAssigned, lunchAssigned)
    }

    private fun getJsonBody(currentCalories: Int, caloriesGap: Int): String {

        val maxCal = caloriesGap - currentCalories

        return """
            {
  "size": 1,
  "plan": {
    "fit": {
      "ENERC_KCAL": {
        "min": ${maxCal / 20},
        "max": ${maxCal / 10}
      },
      "SUGAR.added": {
        "max": 20
      }
    },
    "sections": {
      "Breakfast": {
        "accept": {
          "all": [
            {
              "dish": [
                "drinks",
                "egg",
                "biscuits and cookies",
                "bread",
                "pancake",
                "cereals"
              ]
            },
            {
              "meal": [
                "breakfast"
              ]
            }
          ]
        },
        "fit": {
          "ENERC_KCAL": {
            "min": 25,
            "max": ${maxCal / 30}
          }
        }
      },
      "Lunch": {
        "accept": {
          "all": [
            {
              "dish": [
                "main course",
                "pasta",
                "egg",
                "salad",
                "soup",
                "sandwiches",
                "pizza",
                "seafood"
              ]
            },
            {
              "meal": [
                "lunch/dinner"
              ]
            }
          ]
        },
        "fit": {
          "ENERC_KCAL": {
            "min": 50,
            "max": ${(maxCal / 30) * 2}
          }
        }
      }
    }
  }
}
        """.trimIndent()
    }

    suspend fun getRecipe(uri: String): Recipe? = withContext(Dispatchers.Default) {

        val fullUri = "$baseUrl$uri"
        val url =
            URL("https://api.edamam.com/api/recipes/v2/by-uri?type=public&beta=true&uri=$fullUri&app_id=$appId&app_key=$appKey&field=ingredients&field=ingredientLines&field=images&field=calories&field=totalWeight&field=mealType&field=label&field=uri")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            try {
                val responseCode = responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream.bufferedReader().use {
                        val response = it.readText()
                        try {
                            val hit =
                                JsonParser.parseString(response).asJsonObject.getAsJsonArray("hits")
                                    .get(0)
                            Log.d("SearchAPI", "Hit: $hit")
                            val recipeJson = hit.asJsonObject.getAsJsonObject("recipe")
                            Log.d("SearchAPI", "Recipe JSON: $recipeJson")

                            val imageUris = listOf(
                                ImageType.REGULAR to recipeJson.getAsJsonObject("images")
                                    .getAsJsonObject("REGULAR").get("url").asString,
                                ImageType.THUMBNAIL to recipeJson.getAsJsonObject("images")
                                    .getAsJsonObject("THUMBNAIL").get("url").asString,
                            )
                            Log.d("SearchAPI", "Image URIs: $imageUris")

                            val ingredients =
                                recipeJson.getAsJsonArray("ingredients").map { ingredientJson ->
                                    Ingredient(
                                        text = ingredientJson.asJsonObject.get("text")?.asString
                                            ?: "Unknown",
                                        quantity = ingredientJson.asJsonObject.get("quantity")?.asFloat
                                            ?: 0f,
                                        food = ingredientJson.asJsonObject.get("food")?.asString
                                            ?: "Unknown",
                                        weight = ingredientJson.asJsonObject.get("weight")?.asFloat
                                            ?: 0f,
                                    ).also {
                                        Log.d("SearchAPI", "Ingredient: $it")
                                    }
                                }

                            Recipe(
                                recipeUri = recipeJson.get("uri")?.asString,
                                label = recipeJson.get("label")?.asString ?: "Unknown",
                                imageUris = imageUris,
                                ingredientLines = recipeJson.getAsJsonArray("ingredientLines")
                                    .map { line -> line.asString },
                                ingredients = ingredients,
                                calories = recipeJson.get("calories")?.asFloat ?: 0f,
                                totalWeight = recipeJson.get("totalWeight")?.asFloat ?: 0f,
                                mealType = recipeJson.getAsJsonArray("mealType")?.get(0)?.asString
                                    ?: "Unknown"
                            ).also {
                                Log.d("SearchAPI", "Parsed Recipe: $it")
                            }
                        } catch (e: Exception) {
                            Log.e("SearchAPI", "JsonNullException: ${e.message}")
                            null
                        }
                    }
                } else {
                    Log.e("SearchAPI", "Error: $responseCode")
                    null
                }
            } catch (e: Exception) {
                Log.e("SearchAPI", "Exception: ${e.message}")
                null
            } finally {
                disconnect()
            }
        }
    }

    suspend fun getRecipes(
        query: String,
        minCalories: Int,
        maxCalories: Int
    ): List<Recipe> = withContext(Dispatchers.Default) {

        val url =
            URL("https://api.edamam.com/api/recipes/v2?type=any&beta=true&q=$query&app_id=$appId&app_key=$appKey&ingr=1-15&calories=$minCalories-$maxCalories&imageSize=REGULAR&random=true&field=label&field=images&field=uri&field=image")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            try {
                val responseCode = responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream.bufferedReader().use {
                        val response = it.readText()
                        val hits =
                            JsonParser.parseString(response).asJsonObject.getAsJsonArray("hits")
                        Log.d("SearchAPI", "Entire hits array: $hits")
                        val recipes = hits.mapNotNull { hit ->
                            try {
                                val recipeJson = hit.asJsonObject.getAsJsonObject("recipe")
                                Log.d("SearchAPI", "Recipe JSON: $recipeJson")

                                val imageUris = listOf(
                                    ImageType.THUMBNAIL to recipeJson.getAsJsonObject("images")
                                        .getAsJsonObject("THUMBNAIL").get("url").asString,
                                )

                                Recipe(
                                    recipeUri = recipeJson.get("uri")?.asString ?: "Unknown",
                                    label = recipeJson.get("label")?.asString ?: "Unknown",
                                    imageUris = imageUris,
                                ).also {
                                    Log.d("SearchAPI", "Parsed Recipe: $it")
                                }
                            } catch (e: Exception) {
                                Log.e("SearchAPI", "Error parsing recipe JSON: $hit", e)
                                null
                            }
                        }
                        recipes
                    }

                } else {
                    Log.e("SearchAPI", "Error: $responseCode")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("SearchAPI", "Exception: ${e.message}")
                emptyList()
            } finally {
                disconnect()
            }
        }
    }

    suspend fun getMealPlan(currentCalories: Int, caloriesGap: Int): List<Recipe?>? =
        withContext(Dispatchers.Default) {
            val url = URL("https://api.edamam.com/api/meal-planner/v1/0cd5e30a/select")
            val jsonBody = getJsonBody(currentCalories, caloriesGap)

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                setRequestProperty("accept", "application/json")
                setRequestProperty("Edamam-Account-User", user)
                setRequestProperty("Authorization", auth)
                setRequestProperty("Content-Type", "application/json")
                doOutput = true

                OutputStreamWriter(outputStream).use { it.write(jsonBody) }

                try {
                    val responseCode = responseCode
                    val responseMessage = responseMessage
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream.bufferedReader().use {
                            val recipes = extractSuggestedRecipes(it.readText()).map { recipe ->
                                val temp = recipe?.substringAfterLast("recipe_")
                                getRecipe(temp!!)
                            }
                            recipes
                        }
                    } else {
                        Log.e("SearchAPI", "Error: $responseCode, Message: $responseMessage")
                        null
                    }
                } catch (e: Exception) {
                    Log.e("SearchAPI", "Exception: ${e.message}")
                    null
                } finally {
                    disconnect()
                }
            }
        }


    suspend fun findSuggestedMeals() {
        val recipes = getMealPlan(
            leftoverCalories(),
            AuthenticationManager.getUser().maxCalories!!
        )
        recipes?.let {
            val suggestedMeals = recipes.map { recipe ->
                Meal(
                    recipeUri = recipe?.recipeUri?.substringAfterLast("recipe_").toString(),
                    label = recipe?.label,
                    calories = recipe?.calories,
                    imageUri = recipe?.imageUris?.firstOrNull { it.first == ImageType.REGULAR }?.second
                )
            }
            AuthenticationManager.getUser().suggestedMeals = suggestedMeals
            DatabaseManager.updateUserKey(DatabaseKeys.SUGGESTED_MEALS, suggestedMeals)
        }
    }

}