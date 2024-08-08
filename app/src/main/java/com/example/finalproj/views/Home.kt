package com.example.finalproj.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.finalproj.R
import com.example.finalproj.components.BottomNavigationMenu
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Eat2FitDivider
import com.example.finalproj.components.Eat2FitScaffold
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.components.HeaderLogo
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.SearchAPI
import com.example.finalproj.database.models.ImageType
import com.example.finalproj.database.models.Meal
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.util.InformationPage
import com.example.finalproj.util.InformationPageManager
import com.example.finalproj.util.fetchRecentMeal
import com.example.finalproj.util.icons.rememberBarcodeScanner
import com.example.finalproj.util.loadImageWithRetry

enum class TabSections() {
    INFORMATION_PAGES, QUIZ
}

@Composable
fun HomePage(
    navigateAndClear: (String) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    onInformationPageClick: (String) -> Unit,
) {


//    val suggestedMeal = AuthenticationManager.getUser().suggestedMeals?.get(0)
    val suggestedMeal =
        remember { mutableStateOf<Meal?>(AuthenticationManager.getUser().suggestedMeals?.get(0)) }
    val isFetchingSuggestedMeal = remember { mutableStateOf(true) }
//    val suggestedMealUri = remember { mutableStateOf("") }
    val selectedTab = remember { mutableStateOf(TabSections.INFORMATION_PAGES) }

    LaunchedEffect(suggestedMeal) {

        isFetchingSuggestedMeal.value = true
        SearchAPI.findSuggestedMeals()

//        val recipe = SearchAPI.getMealPlan(
//            leftoverCalories(),
//            AuthenticationManager.getUser().maxCalories!!
//        )?.get(1)
//        recipe?.let {
//            suggestedMeal.value = Meal(
//                label = recipe.label,
//                calories = recipe.calories,
//                imageUri = recipe.imageUris?.firstOrNull { it.first == ImageType.REGULAR }?.second
//            )
//            suggestedMealUri.value = recipe.recipeUri?.substringAfterLast("recipe_").toString()
//        }
        suggestedMeal.value = AuthenticationManager.getUser().suggestedMeals?.get(0)
        isFetchingSuggestedMeal.value = false

    }


    Eat2FitScaffold(
        bottomBar = {
            BottomNavigationMenu(
                tabs = AppSections.values(),
                currentRoute = AppSections.HOME.route,
                navigateToRoute = navigateAndClear
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
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(rememberScrollState(), reverseScrolling = false)

            ) {
//                recentMeal.value?.let {
                RecentProducts_Section(
                    navigateAndClear,
                    onNavigateToRoute
                )
                SuggestedMealSection(
                    suggestedMeal.value,
                    isFetchingSuggestedMeal.value,
                    onNavigateToRoute,
                    suggestedMeal.value?.recipeUri!!
                )
                InformationPagesQuizSection(
                    selectedTab,
                    onNavigateToRoute,
                    onInformationPageClick
                )
            }
        }
    }
}

@Composable
fun RecentProducts_Section(
    navigateAndClear: (String) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    recentMeal: Meal? = AuthenticationManager.getUser().recentMeal,

    ) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val loadingState = remember { mutableStateOf(false) }

    LazyRow(
        modifier = Modifier
            .padding(30.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(text = "Recent Meal", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                if (loadingState.value) {
                    CircularProgressIndicator()
                } else if (recentMeal == null) {
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(250.dp)
                            .background(Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No meals watched recently",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    SubcomposeAsyncImage(
                        model = loadImageWithRetry(
                            context = context,
                            imageUri = recentMeal.imageUri!!,
                            onError = {
                                fetchRecentMeal(
                                    coroutineScope,
                                    recentMeal,
                                    loadingState,
                                    true
                                )
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 200.dp, height = 200.dp),
                        loading = { CircularProgressIndicator() },
                    )
                    Text(
                        modifier = Modifier.width(250.dp),
                        text = recentMeal.label ?: "Unknown",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .background(Eat2FitTheme.colors.emptyGreen, RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .clickable { onNavigateToRoute(AppSections.SEARCH.route) },
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Search for a Meal",
                        fontSize = 14.sp
                    )
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .background(Eat2FitTheme.colors.emptyGreen, RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .clickable {
                        onNavigateToRoute(AppSections.BARCODE_SCAN.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = rememberBarcodeScanner(),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Scan a Product",
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
    Eat2FitDivider()
}

@Composable
fun SuggestedMealSection(
    suggestedMeal: Meal? = null,
    isFetching: Boolean,
    onNavigateToRoute: (String) -> Unit,
    suggestedMealUri: String
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val loadingState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 5.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Suggested Meal",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        when {
            isFetching -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color.LightGray, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            suggestedMeal == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color.LightGray, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Couldn't find an appropriate meal for you",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                        .clickable {
                            onNavigateToRoute("${AppSections.SEARCH.route}/$suggestedMealUri")
                        }
                ) {
                    SubcomposeAsyncImage(
                        model = loadImageWithRetry(
                            context = context,
                            imageUri = suggestedMeal.imageUri!!,
                            onError = {
                                fetchRecentMeal(
                                    coroutineScope,
                                    suggestedMeal,
                                    loadingState,
                                    isSuggestedMeal = true
                                )
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 200.dp, height = 200.dp),
                        loading = { CircularProgressIndicator() },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = suggestedMeal.label!!,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Calories: ${roundToHalf(suggestedMeal.calories!!)}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
    Eat2FitDivider()
}

@Composable
fun InformationPagesQuizSection(
    selectedTab: MutableState<TabSections>,
    onNavigateToRoute: (String) -> Unit,
    onInformationPageClick: (String) -> Unit

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Eat2FitButton(
                onClick = { selectedTab.value = TabSections.INFORMATION_PAGES },
                backgroundGradient = if (selectedTab.value == TabSections.INFORMATION_PAGES) Eat2FitTheme.colors.orange else Eat2FitTheme.colors.whiteGradiant,
                contentColor = if (selectedTab.value == TabSections.INFORMATION_PAGES) Color.White else Color.Gray,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "All",
                    fontWeight = if (selectedTab.value == TabSections.INFORMATION_PAGES) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Eat2FitButton(
                onClick = { selectedTab.value = TabSections.QUIZ },
                backgroundGradient = if (selectedTab.value == TabSections.QUIZ) Eat2FitTheme.colors.orange else Eat2FitTheme.colors.whiteGradiant,
                contentColor = if (selectedTab.value == TabSections.QUIZ) Color.White else Color.Gray,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Quiz",
                    fontWeight = if (selectedTab.value == TabSections.QUIZ) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        val columnHeight =
            825.dp//if (selectedTab.value == TabSections.INFORMATION_PAGES) 800.dp else 200.dp

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(columnHeight)
        ) {
            if (selectedTab.value == TabSections.INFORMATION_PAGES) {
                InformationPageManager.informationPages.forEachIndexed { index, page ->
                    InformationPageCard(page, onNavigateToRoute, onInformationPageClick)
                    if (index != InformationPageManager.informationPages.size - 1)
                        Eat2FitDivider()
                }
            } else {
                QuizColumn(onNavigateToRoute)
            }
        }
    }
}

@Composable
fun InformationPageCard(
    page: InformationPage,
    onNavigateToRoute: (String) -> Unit,
    onInformationPageClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable { onInformationPageClick(page.infoPageId) }
            .padding(16.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painterResource(id = page.imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(15.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {

                Text(
                    text = page.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = page.openingQuestion,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun QuizColumn(
    onNavigateToRoute: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable { onNavigateToRoute("${AppSections.HOME.route}/quiz") }
            .padding(16.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painterResource(id = R.drawable.quiz),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(15.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {

                Text(
                    text = "Quiz",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Take a quiz to test your knowledge",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

