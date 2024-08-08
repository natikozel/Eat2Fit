package com.example.finalproj.views

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.finalproj.ui.theme.Eat2FitTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.finalproj.R
import com.example.finalproj.components.BottomNavigationMenu
import com.example.finalproj.components.CircularDeterminateIndicator
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Eat2FitDivider
import com.example.finalproj.components.Eat2FitScaffold
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.DatabaseKeys
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.StorageManager
import com.example.finalproj.database.models.Goal
import com.example.finalproj.database.models.User
import com.example.finalproj.util.Destinations
import com.example.finalproj.util.icons.rememberBarcodeScanner
import com.example.finalproj.util.icons.rememberFastfood
import kotlin.math.max
import kotlin.math.min
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.finalproj.database.models.Meal
import com.example.finalproj.database.models.QuizQuestions
import com.example.finalproj.util.PermissionRequester


private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val BottomBarHeight = 56.dp
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val UserDetailsHeight = 180.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val HzPadding = Modifier.padding(horizontal = 24.dp)


enum class AppSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.feed, Icons.Outlined.Home, "home"),
    SEARCH(R.string.search, Icons.Outlined.Search, "search"),
    BARCODE_SCAN(R.string.scanner, rememberBarcodeScanner(), "barcode_scanner"),
    MEALS(R.string.meals, rememberFastfood(), "meals"),
    PROFILE(R.string.profile, Icons.Outlined.AccountCircle, "myProfile"),

}


fun NavGraphBuilder.addAppGraph(
    onNavigateToRoute: (String) -> Unit,
    popBack: () -> Boolean,
    navigateAndClear: (String) -> Unit
) {
    composable(
        AppSections.PROFILE.route,
        enterTransition = { fadeIn() },
    ) {

        BackHandler(true) {
            // pass
        }
        ProfilePage(onNavigateToRoute, navigateAndClear)
    }
    composable(
        "${AppSections.PROFILE.route}/edit",
        enterTransition = { fadeIn() },
//        exitTransition = { slideOutOfContainer() }
    ) {
        EditProfile(popBack)
    }
    composable(
        AppSections.BARCODE_SCAN.route,
    ) {
        BarcodeScan(
            popBack,
            onSuccessfulScan = { barcode -> onNavigateToRoute("${AppSections.BARCODE_SCAN.route}/$barcode") },
            onNavigateToRoute
        )
    }

    composable(
        AppSections.SEARCH.route, // profile/search
        enterTransition = { fadeIn() },
    ) {
        Search(
            onNavigateToRoute,
            onSearchResultClick = { recipeUri -> onNavigateToRoute("${AppSections.SEARCH.route}/$recipeUri") })
    }

    composable(
        AppSections.MEALS.route,
        enterTransition = { fadeIn() },
    ) {
        MealsView(navigateAndClear)
    }

    composable(
        AppSections.HOME.route,
        enterTransition = { fadeIn() },
    ) {
        HomePage(
            navigateAndClear,
            onNavigateToRoute,
            onInformationPageClick = { infoPageId -> onNavigateToRoute("${AppSections.HOME.route}/$infoPageId") }
        )
    }

    composable(
        "${AppSections.HOME.route}/quiz",
        enterTransition = { slideIntoContainer() },
    ) {
        QuizIntroductionScreen(
            popBack,
            navigateToQuiz = { onNavigateToRoute("${AppSections.HOME.route}/quiz/question") })
    }
    composable(
        "${AppSections.HOME.route}/quiz/question",
        enterTransition = { fadeIn() },
    ) {
        QuizQuestionScreen(navigateAndClear, onNavigateToRoute)
    }

}


@Composable
fun ProfilePage(
    onNavigateToRoute: (String) -> Unit,
    navigateAndClear: (String) -> Unit,
) {

    val user = AuthenticationManager.getUser()
    val currentCalories = calculateCalories(user.previousMeals)


    Eat2FitScaffold(
        bottomBar = {
            BottomNavigationMenu(
                tabs = AppSections.values(),
                currentRoute = AppSections.PROFILE.route,
                navigateToRoute = onNavigateToRoute
            )
        }
    )
    {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            val scroll = rememberScrollState(0)
            Header()
            Body(scroll, user, currentCalories, user.maxCalories)
            Title(
                navigateAndClear = navigateAndClear,
                onNavigateToRoute = onNavigateToRoute,
                user = user,
            ) { scroll.value }
            Image(user = user) { scroll.value }


        }
    }
}

@Composable
private fun Header() {
    val brushColors = Eat2FitTheme.colors.green
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    val targetOffset = with(LocalDensity.current) {
        3000.dp.toPx()
    }
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = targetOffset,
        animationSpec = infiniteRepeatable(
            tween(50000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )

    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .blur(40.dp)
            .drawWithCache {
                val brushSize = 400f
                val brush = Brush.linearGradient(
                    colors = brushColors,
                    start = Offset(offset, offset),
                    end = Offset(offset + brushSize, offset + brushSize),
                    tileMode = TileMode.Mirror
                )
                onDrawBehind {
                    drawRect(brush)
                }
            }

    )
}

@Composable
private fun Title(
    user: User,
    onNavigateToRoute: (String) -> Unit,
    navigateAndClear: (String) -> Unit,
    scrollProvider: () -> Int,
) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    Column(
        verticalArrangement = Arrangement.spacedBy(
            8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .heightIn(min = UserDetailsHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = Eat2FitTheme.colors.uiBackground)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(text = capitalizeName(user.fullName!!), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
            RoundFrame("${user.height} cm", "Height")
            RoundFrame("${user.weight} kg", "Weight")
            RoundFrame("${user.age}", "Age")
        }
        Spacer(Modifier.height(4.dp))
        Eat2FitButton(onClick = { onNavigateToRoute("${AppSections.PROFILE.route}/edit") }) {
            Text(
                text = "Edit",
                modifier = Modifier.width(40.dp),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
        Eat2FitButton(onClick = {
            AuthenticationManager.logoutUser()
            navigateAndClear(Destinations.LANDING)
        }) {
            Text(
                text = "Log out",
                modifier = Modifier.width(40.dp),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
        Spacer(Modifier.height(8.dp))
        Eat2FitDivider()
    }
}


@Composable
private fun RoundFrame(data: String, title: String) {
    Box(
        Modifier
            .shadow(10.0.dp, shape = RoundedCornerShape(16.0.dp))
            .clip(RoundedCornerShape(16.0.dp))
            .size(95.0.dp, 65.0.dp)
            .background(Color(1.0f, 1.0f, 1.0f, 1.0f))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        )
        {


            Text(
                text = data,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF92A3FD),
                )
            )
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7B6F72),
                )
            )
        }
    }

}

@Composable
fun Notification_Section(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = NotificationViewModel()
) {
    val context = LocalContext.current
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    var showPermissionRequester by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.checkNotificationSettings(context)
    }

    if (showPermissionRequester) {
        PermissionRequester(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            rationale = "Notification permission is required to show notifications."
        ) { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Notifications enabled", Toast.LENGTH_SHORT).show()
                viewModel.setNotificationsEnabled(true)
            } else {
                Toast.makeText(context, "Notifications disabled", Toast.LENGTH_SHORT).show()
                viewModel.setNotificationsEnabled(false)
            }
            showPermissionRequester = false
        }
    }
    Box(
        modifier
            .fillMaxSize()
            .shadow(10.0.dp, shape = RoundedCornerShape(16.0.dp))
            .clip(RoundedCornerShape(16.0.dp))
            .size(325.0.dp, 109.0.dp)
            .background(Color(1.0f, 1.0f, 1.0f, 1.0f))
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Notification",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF1D1617),
                )
            )
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Pop-up Notification",
                    Modifier.wrapContentHeight(Alignment.Top),
                    style = LocalTextStyle.current.copy(
                        color = Color(0.48f, 0.44f, 0.45f, 1.0f),
                        textAlign = TextAlign.Left,
                        fontSize = 12.0.sp
                    )
                )
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            showPermissionRequester = true
                        } else {
                            viewModel.revokeNotificationPermission(context)
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Eat2FitTheme.colors.white,
                        checkedTrackColor = Eat2FitTheme.colors.lightGreen,
                        uncheckedThumbColor = Eat2FitTheme.colors.white,
                        uncheckedTrackColor = Eat2FitTheme.colors.emptyGreen,
                    )
                )
            }
        }
    }
}

@Composable
private fun Body(
    scroll: ScrollState,
    user: User,
    currentCalories: Int,
    availableCalories: Int?
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            Spacer(Modifier.height(130.dp))
            Eat2FitSurface(Modifier.fillMaxWidth()) {
                Column(
                    Modifier
                        .fillMaxSize(),

                    ) {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))
                    Spacer(Modifier.height(32.dp))
                    Notification_Section(modifier = HzPadding)
                    Spacer(Modifier.height(32.dp))
                    DailyProgress_Section(
                        user,
                        currentCalories,
                        availableCalories
                    )
                    Spacer(Modifier.height(32.dp))
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = BottomBarHeight)
                            .navigationBarsPadding()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun DailyProgress_Section(
    user: User,
    currentCalories: Int,
    availableCalories: Int?
) {

    Text(
        "Daily progress",
        Modifier
            .padding(start = 16.dp, top = 30.dp),
        style = LocalTextStyle.current.copy(
            color = Color(0.25f, 0.27f, 0.27f, 1.0f),
            textAlign = TextAlign.Left,
            fontSize = 24.sp
        ),
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(32.dp))
    Column(
        Modifier
            .fillMaxSize()
            .padding(1.dp),
        verticalArrangement = Arrangement.Center,
//                            .padding(24.dp)
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = HzPadding
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x0F101828),
                    ambientColor = Color(0x0F101828)
                )
                .shadow(
                    elevation = 3.dp,
                    spotColor = Color(0x1A101828),
                    ambientColor = Color(0x1A101828)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFE3E8EF),
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {

            Row(
                Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.spacedBy(217.0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        20.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Text(
                        text = "My calories",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 28.sp,
                            fontFamily = FontFamily(Font(R.font.karla_bold)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF121926),
                            textAlign = TextAlign.Right,
                        )
                    )


                    CircularDeterminateIndicator(
                        progress = calculateProgress(availableCalories ?: 0, currentCalories),
                        currentCalories = currentCalories.toString(),
                        availableCalories = availableCalories.toString(),
                        modifier = Modifier
//                            .align(Alignment.TopCenter)
//                            .offset(y = 98.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        20.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Icon
                        // Padding
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                4.dp,
                                alignment = Alignment.CenterVertically
                            ), horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = testBasedOnGoal(user.goal!!), style = TextStyle(
                                    fontSize = 20.sp,
                                    lineHeight = 28.sp,
                                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF121926),
                                    textAlign = TextAlign.Right,
                                )
                            )
                            Text(
                                text = availableCalories.toString(),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 28.sp,
                                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF121926),
                                    textAlign = TextAlign.Right,
                                )
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween                        // Icon
                        // Padding
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                4.dp,
                                alignment = Alignment.CenterVertically
                            ), horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Remaining", style = TextStyle(
                                    fontSize = 20.sp,
                                    lineHeight = 28.sp,
                                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF121926),
                                    textAlign = TextAlign.Right,
                                )
                            )
                            Text(
                                text = calculateRemaining(
                                    availableCalories ?: 0,
                                    currentCalories
                                ),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 28.sp,
                                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF121926),
                                    textAlign = TextAlign.Right,
                                )
                            )
                        }
                    }


                }

            }
        }

    }
}

fun calculateRemaining(
    availableCalories: Int,
    currentCalories: Int,
): String {
    if (availableCalories - currentCalories < 0) {
        return "Excess eating"
    } else {
        return (availableCalories - currentCalories).toString()
    }
}


private fun testBasedOnGoal(goal: Goal): String {
    if (goal == Goal.LOSE)
        return "Limit"
    else if (goal == Goal.GAIN)
        return "Minimum"
    else
        return "Goal"
}

@Composable
private fun Image(
    user: User,
    scrollProvider: () -> Int
) {
    val collapseRange =
        with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.statusBarsPadding()
    ) {
        UserImage(
//            initialImageUrl = user.imageUrl,
            contentDescription = null,
            user = user,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth) - 220
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable =
            measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            (constraints.maxWidth - imageWidth) / 2, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Composable
fun UserImage(
    modifier: Modifier = Modifier,
    user: User,
    contentDescription: String?,
    elevation: Dp = 0.dp
) {
    var imageUrl by remember { mutableStateOf(user.imageUrl) }

    Eat2FitSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        MyImageArea(
            uri = imageUrl,
            directory = LocalContext.current.cacheDir,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            onSetUri = { uri ->
                val newImageUrl = uri.toString()
                imageUrl = newImageUrl

                StorageManager.uploadImage(uri, onSuccess = {
                    DatabaseManager.updateUserKey(DatabaseKeys.IMAGE_URL, it)
                }, onFailure = {
                })
            },
            onRemoveUri = {
                imageUrl = null
                DatabaseManager.updateUserKey(DatabaseKeys.IMAGE_URL, null)

            }
        )
    }
}


fun capitalizeName(name: String): String {
    return name.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}


fun calculateProgress(caloriesGoal: Int, currentCalories: Int): Float {

    if (caloriesGoal < currentCalories)
        return 100f

    return (currentCalories * 100) / caloriesGoal.toFloat()
}

fun calculateCalories(meals: HashMap<String, Meal>?): Int {
    var totalCalories = 0
    if (meals != null) {
        for (meal in meals) {
            totalCalories += meal.value.calories!!.toInt()
        }
    }
    return totalCalories
}

fun leftoverCalories(): Int {
    val user = AuthenticationManager.getUser()
    val userCalories = user.maxCalories
    val currentCalories = calculateCalories(user.previousMeals)
    return if (userCalories != null) {
        userCalories - currentCalories
    } else {
        0
    }
}


fun navigateToProfile(
    navigateAndClear: (String) -> Unit
): Boolean {
    navigateAndClear(AppSections.PROFILE.route)
    return true
}

fun navigateToHome(
    navigateAndClear: (String) -> Unit
): Boolean {
    navigateAndClear(AppSections.HOME.route)
    return true
}


class NotificationViewModel : ViewModel() {
    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    fun checkNotificationSettings(context: Context) {
        viewModelScope.launch {
            val enabled = NotificationManagerCompat.from(context).areNotificationsEnabled()
            _notificationsEnabled.value = enabled
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
    }

    fun revokeNotificationPermission(context: Context) {
        viewModelScope.launch {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }
    }
}