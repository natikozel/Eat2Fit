package com.example.finalproj.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.finalproj.R
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Eat2FitScaffold
import com.example.finalproj.components.HeaderLogo
import com.example.finalproj.components.NavigateBackArrow
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.models.ImageType
import com.example.finalproj.database.models.Recipe
import com.example.finalproj.database.SearchAPI
import com.example.finalproj.database.models.Meal
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.util.roundToHalf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


enum class RecipeDisplay {
    Fetching, Results, NoResults
}

@Stable
class RecipeState(
    recipe: Recipe? = null,
    fetching: Boolean,
) {

    var recipe by mutableStateOf(recipe)
    var fetching by mutableStateOf(fetching)
    val recipeDisplay: RecipeDisplay
        get() = when {
            recipe != null -> RecipeDisplay.Results
            fetching -> RecipeDisplay.Fetching
            else -> RecipeDisplay.NoResults

        }
}

@Composable
private fun rememberRecipeState(
    recipe: Recipe? = null,
    fetching: Boolean = false,
): RecipeState {
    return remember {
        RecipeState(
            recipe = recipe,
            fetching = fetching
        )
    }
}

@Composable
fun RecipeDetail(
    recipeUri: String,
    popBack: () -> Boolean,
    onNavigateToRoute: (String) -> Unit,
    recipeState: RecipeState = rememberRecipeState()

) {

    val coroutineScope = rememberCoroutineScope()
    var showPopup by remember { mutableStateOf(false) }

    LaunchedEffect(recipeUri) {
        recipeState.fetching = true
        delay(100)
        recipeState.recipe = SearchAPI.getRecipe(recipeUri)
        DatabaseManager.updateRecentlyWatchedMeal(getMealFromState(recipeState))
        recipeState.fetching = false;
    }

    if (showPopup) {
        MealAddedPopup(onDismiss = { showPopup = false }, onNavigateToRoute)
    }

    Eat2FitScaffold(
        topBar = { HeaderLogo() }
    )
    {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp)
                .navigationBarsPadding()
        ) {

            when (recipeState.recipeDisplay) {
                RecipeDisplay.NoResults -> {
                    item {
                        Spacer(Modifier.height(90.dp))
                        Text(
                            text = stringResource(R.string.no_recipe_found),
                            style = MaterialTheme.typography.titleLarge,
                            color = Eat2FitTheme.colors.textPrimary,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        )
                    }
                }

                RecipeDisplay.Fetching -> {
                    item {
                        Spacer(Modifier.height(90.dp))
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                RecipeDisplay.Results -> {
                    item {
                        Spacer(Modifier.height(90.dp))
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.Top
                            )
                        ) {

                            NavigateBackArrow(popBack)

                            recipeState.recipe?.label?.let {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = it,
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        lineHeight = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.karla_bold)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF1D1617),
                                    ),
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        recipeState.recipe?.imageUris?.firstOrNull { it.first == ImageType.LARGE }?.second
                                            ?: recipeState.recipe?.imageUris?.firstOrNull { it.first == ImageType.REGULAR }?.second
                                            ?: ""
                                    )
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                            )


                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.ingredients),
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF010101),
                                ),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            recipeState.recipe?.ingredients?.forEach { ingredient ->
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = "${
                                            if (ingredient.quantity.toFloat() == 0.0f) "" else "${
                                                roundToHalf(
                                                    ingredient.quantity
                                                )
                                            } "
                                        }${ingredient.food}",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            lineHeight = 24.sp,
                                            color = Color(0xFF1D1617),
                                        ),
                                    )
                                    Text(
                                        text = ingredient.text,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontFamily = FontFamily(Font(R.font.karla_bold)),
                                            color = Color(0xFFC0C0C0),
                                        ),
                                    )
                                    Spacer(Modifier.height(20.dp))
                                }
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = stringResource(R.string.recipe_details),
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        lineHeight = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF010101),
                                    ),
                                )
                                Spacer(Modifier.height(20.dp))

                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(R.string.meal_type),
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            lineHeight = 24.sp,
                                            color = Color(0xFF1D1617),
                                        ),
                                    )
                                    Text(
                                        text = recipeState.recipe?.mealType
                                            ?: stringResource(R.string.unknown),
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            lineHeight = 24.sp,
                                            color = Color(0xFF1D1617),
                                        ),
                                    )
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 20.dp, bottom = 20.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(R.string.total_calories),
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            lineHeight = 24.sp,
                                            color = Color(0xFF1D1617),
                                        ),
                                    )
                                    Text(
                                        text = "${roundToHalf(recipeState.recipe?.calories!!)}",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            lineHeight = 24.sp,
                                            color = Color(0xFF1D1617),
                                        ),
                                    )
                                }
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(R.string.total_weight),
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            lineHeight = 24.sp,
                                            color = Color(0xFF1D1617),
                                        ),
                                    )
                                    Text(
                                        text = "${roundToHalf(recipeState.recipe?.totalWeight!!)} ${
                                            stringResource(
                                                R.string.grams
                                            )
                                        }",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            lineHeight = 24.sp,
                                            color = Color(0xFF1D1617),
                                        ),
                                    )
                                }
                                Spacer(Modifier.height(20.dp))

                                Eat2FitButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        coroutineScope.launch {
                                            DatabaseManager.writeMeal(getMealFromState(recipeState))
                                            showPopup = true

                                        }
                                    }) {
                                    Text(
                                        text = stringResource(R.string.add_to_my_meals),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MealAddedPopup(onDismiss: () -> Unit, onNavigateToRoute: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.meal_added),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(text = stringResource(R.string.meal_added_message))
        },
        confirmButton = {
            Eat2FitButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onDismiss()
                    onNavigateToRoute(AppSections.MEALS.route)
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

private fun getMealFromState(recipeState: RecipeState): Meal {
    return Meal(
        recipeUri = recipeState.recipe?.recipeUri,
        imageUri = recipeState.recipe?.imageUris?.firstOrNull { it.first == ImageType.REGULAR }?.second
            ?: "",
        label = recipeState.recipe?.label ?: "Unknown",
        calories = recipeState.recipe?.calories ?: 0f
    )
}