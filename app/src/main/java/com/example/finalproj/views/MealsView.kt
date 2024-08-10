package com.example.finalproj.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.finalproj.R
import com.example.finalproj.components.BottomNavigationMenu
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Eat2FitScaffold
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.components.HeaderLogo
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.models.Meal
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.util.fetchRecentMeal
import com.example.finalproj.util.loadImageWithRetry
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MealsView(
    onNavigateToRoute: (String) -> Unit,
) {
    var meals =
        AuthenticationManager.getUser().previousMeals
    val coroutineScope = rememberCoroutineScope()
    var showPopup by remember { mutableStateOf(false) }


    if (showPopup) {
        MealRemovePopup(onDismiss = { showPopup = false }, onNavigateToRoute)
    }


    Eat2FitScaffold(
        bottomBar = {
            BottomNavigationMenu(
                tabs = AppSections.values(),
                currentRoute = AppSections.MEALS.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        topBar = { HeaderLogo() }
    )
    { paddingValues ->
        Eat2FitSurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            meals.let {
                if (it.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.mealsview_error_title),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Eat2FitTheme.colors.textPrimary
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.mealsview_title),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Eat2FitTheme.colors.textPrimary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center
                        )

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(it.entries.toList()) { (key, meal) ->
                                MealCard(meal = meal, index = key, onDelete = {
                                    coroutineScope.launch {
                                        val updatedMeals = HashMap(meals)
                                        updatedMeals.remove(key)
                                        meals = updatedMeals
                                        DatabaseManager.deleteMeal(key).addOnFailureListener {
                                            updatedMeals[key] = meal
                                            meals = updatedMeals
                                        }
                                        showPopup = true
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MealCard(meal: Meal, index: String, onDelete: () -> Unit) {


    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val loadingState = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)

        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                if (loadingState.value) {
                    CircularProgressIndicator()
                } else {
                    SubcomposeAsyncImage(
                        model = loadImageWithRetry(
                            context = context,
                            imageUri = meal.imageUri!!,
                            onError = {
                                fetchRecentMeal(
                                    coroutineScope,
                                    meal,
                                    loadingState,
                                    index = index
                                )
                            }),
                        contentDescription = null,
                        modifier = Modifier.height(400.dp),
                        loading = { CircularProgressIndicator() },
                    )


                    meal.label?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            style = TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1D1617),
                            ),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {


                        Text(
                            text = stringResource(R.string.calories_text, meal.calories!!.toInt()),
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1D1617),
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        IconButton(onClick = onDelete) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = stringResource(R.string.delete_meal),
                                modifier = Modifier.size(width = 70.dp, height = 70.dp),
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun MealRemovePopup(onDismiss: () -> Unit, onNavigateToRoute: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.meal_removed),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(text = stringResource(R.string.meal_removed_message))
        },
        confirmButton = {
            Eat2FitButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onDismiss()
                }) {
                Text(
                    stringResource(R.string.continue_label),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}