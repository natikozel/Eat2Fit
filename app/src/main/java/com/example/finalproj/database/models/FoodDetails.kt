package com.example.finalproj.database.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.JsonParser

data class FoodDetails(
    val product_name: String,
    val ingredients_text: String,
    val image_url: String,
    val energy: String,
)

class FoodViewModel : ViewModel() {
    private val _foodDetails = MutableStateFlow<FoodDetails?>(null)
    val foodDetails: StateFlow<FoodDetails?> = _foodDetails

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchFoodDetails(barcode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val url = URL("https://world.openfoodfacts.org/api/v3/product/$barcode.json")

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                try {
                    val responseCode = responseCode
                    Log.d("FoodViewModel", "Response Code: $responseCode")
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream.bufferedReader().use {
                            val response = it.readText()
                            val jsonObject = JsonParser.parseString(response).asJsonObject
                            val product = jsonObject.getAsJsonObject("product")
                            val foodDetails = FoodDetails(
                                product_name = product.get("product_name")?.asString ?: "Unknown",
                                ingredients_text = product.get("ingredients_text")?.asString ?: "Unknown",
                                image_url = product.get("image_url")?.asString ?: "",
                                energy = product.get("energy")?.asString ?: ""
                            )
                            _foodDetails.value = foodDetails
                        }
                    } else {
                        Log.e("FoodViewModel", "Error: $responseCode")
                        _foodDetails.value = null
                    }
                } catch (e: Exception) {
                    Log.e("FoodViewModel", "Exception: ${e.message}")
                    _foodDetails.value = null
                } finally {
                    _isLoading.value = false
                    disconnect()
                }
            }
        }
    }
}