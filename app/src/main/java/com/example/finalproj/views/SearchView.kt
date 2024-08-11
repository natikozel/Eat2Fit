package com.example.finalproj.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.finalproj.R
import com.example.finalproj.components.BottomNavigationMenu
import com.example.finalproj.components.Eat2FitDivider
import com.example.finalproj.components.Eat2FitScaffold
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.components.MealImage
import com.example.finalproj.components.SearchBar
import com.example.finalproj.models.ImageType
import com.example.finalproj.models.Recipe
import com.example.finalproj.database.SearchAPI
import com.example.finalproj.ui.theme.Eat2FitTheme
import kotlinx.coroutines.delay

enum class SearchDisplay {
    Meals, Suggestions, Results, NoResults
}

@Composable
private fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    searchResults: List<Recipe> = emptyList()
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            searchResults = searchResults
        )
    }
}

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    searchResults: List<Recipe>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var isDoneFetching by mutableStateOf(false)
    var searchResults by mutableStateOf(searchResults)
    val searchDisplay: SearchDisplay
        get() = if (!focused && query.text.isEmpty()) {
            SearchDisplay.Meals
        } else if (focused && query.text.isEmpty()) {
            SearchDisplay.Suggestions
        } else if (focused && query.text.isNotEmpty() && searchResults.isEmpty() && isDoneFetching) {
            SearchDisplay.NoResults
        } else if (focused && query.text.isNotEmpty() && searchResults.isNotEmpty() && isDoneFetching) {
            SearchDisplay.Results
        } else {
            SearchDisplay.Meals
        }
}

@Composable
fun Search(
    onNavigateToRoute: (String) -> Unit,
    state: SearchState = rememberSearchState(),
    onSearchResultClick: (String) -> Unit,
) {
    Eat2FitScaffold(
        bottomBar = {
            BottomNavigationMenu(
                tabs = AppSections.values(),
                currentRoute = AppSections.SEARCH.route,
                navigateToRoute = onNavigateToRoute
            )
        }
    )
    { paddingValues ->
        Eat2FitSurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(Modifier.fillMaxSize()) {
                SearchBar(
                    query = state.query,
                    onQueryChange = { state.query = it },
                    searchFocused = state.focused,
                    onSearchFocusChange = { state.focused = it },
                    onClearQuery = { state.query = TextFieldValue("") },
                    searching = state.searching
                )
                Eat2FitDivider()

                LaunchedEffect(state.query.text) {
                    state.searching = true
                    delay(300)
                    if (state.query.text.isNotEmpty()) {
                        state.searchResults = SearchAPI.getRecipes(state.query.text, 0, 1000)
                        state.isDoneFetching = true
                    }

                    state.searching = false
                }

                if (state.searching) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                when (state.searchDisplay) {


                    SearchDisplay.Meals -> {
                        // no-op
                        // Suggested meals are displayed
                    }

                    SearchDisplay.Suggestions -> {
                        // no-op
                        // Recent Searches
                    }


                    SearchDisplay.NoResults -> NoResults(state.query.text)

                    SearchDisplay.Results -> SearchResults(
                        state.searchResults,
                        onSearchResultClick
                    )
                }
            }
        }
    }
}



@Composable
fun SearchResults(
    searchResults: List<Recipe>,
    onRecipeClick: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.search_count, searchResults.size),
            style = MaterialTheme.typography.titleSmall,
            color = Eat2FitTheme.colors.textPrimary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )
        LazyColumn {
            itemsIndexed(searchResults) { index, meal ->
                SearchResult(meal, onRecipeClick, index != 0)
            }
        }
    }
}

@Composable
private fun SearchResult(
    meal: Recipe,
    onRecipeClick: (String) -> Unit,
    showDivider: Boolean,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                meal.recipeUri?.let { uri ->
                    val recipeId = uri.substringAfterLast("recipe_")
                    onRecipeClick(recipeId)
                }
            }
            .padding(horizontal = 24.dp)

    ) {
        val (divider, image, name, tag, priceSpacer, price, add) = createRefs()

        createVerticalChain(name, tag, priceSpacer, price, chainStyle = ChainStyle.Packed)
        if (showDivider) {
            Eat2FitDivider(
                Modifier
                    .constrainAs(divider)
                    {
                        linkTo(start = parent.start, end = parent.end)
                        top.linkTo(parent.top)
                    }
            )
        }
        MealImage(
            imageRes = meal.imageUris!!.firstOrNull { it.first == ImageType.THUMBNAIL }?.second
                ?: "",
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(image) {
                    linkTo(
                        top = parent.top,
                        topMargin = 16.dp,
                        bottom = parent.bottom,
                        bottomMargin = 16.dp
                    )
                    start.linkTo(parent.start)
                }
        )
        meal.label?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge,
                color = Eat2FitTheme.colors.textSecondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 150.dp)
                    .constrainAs(name) {
                        linkTo(
                            start = image.end,
                            startMargin = 16.dp,
                            end = add.start,
                            endMargin = 16.dp,
                            bias = 0f
                        )
                    },
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(
            Modifier
                .height(8.dp)
                .constrainAs(priceSpacer) {
                    linkTo(top = tag.bottom, bottom = price.top)
                }
        )
    }
}


@Composable
fun NoResults(
    query: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        androidx.compose.foundation.Image(
            painterResource(R.drawable.empty_state_search),
            contentDescription = null
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.search_no_matches, query),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.search_no_matches_retry),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}