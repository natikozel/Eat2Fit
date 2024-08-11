package com.example.finalproj.views


import kotlinx.coroutines.launch
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
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
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.models.Goal
import com.example.finalproj.database.models.User
import com.example.finalproj.util.Destinations
import com.example.finalproj.util.icons.rememberBarcodeScanner
import com.example.finalproj.util.icons.rememberFastfood
import android.provider.Settings
import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.Icon
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.example.finalproj.components.CollapsingImageLayout
import com.example.finalproj.components.CustomAlertDialog
import com.example.finalproj.components.RoundFrame
import com.example.finalproj.components.UserImage
import com.example.finalproj.util.calculateCalories
import com.example.finalproj.util.calculateProgress
import com.example.finalproj.util.calculateRemaining
import com.example.finalproj.util.capitalizeName
import com.example.finalproj.util.icons.rememberAccessibility
import com.example.finalproj.util.icons.rememberRemainingFood
import com.example.finalproj.util.testBasedOnGoal


private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val BottomBarHeight = 56.dp
val ExpandedImageSize = 300.dp
val CollapsedImageSize = 150.dp
private val ImageOverlap = 115.dp
val MinTitleOffset = 56.dp
private val UserDetailsHeight = 180.dp
val MinImageOffset = 12.dp
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


        val isLoading = remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            isLoading.value = true
            DatabaseManager.readUser().addOnSuccessListener { dataSnapshot ->
                val data = dataSnapshot.getValue(User::class.java)
                AuthenticationManager.setUser(data!!)
                isLoading.value = false
            }
        }

        if (isLoading.value)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        else
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
    user: User = AuthenticationManager.getUser()
) {

    val currentCalories = calculateCalories()
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


    val logoutDialog = remember { mutableStateOf(false) }

    if (logoutDialog.value) {

        CustomAlertDialog(
            onDismiss = {
                logoutDialog.value = false
            },
            onExit = {
                AuthenticationManager.logoutUser()
                navigateAndClear(Destinations.LANDING)
                logoutDialog.value = false

            })
    }

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
            RoundFrame("${user.height} cm", stringResource(R.string.height))
            RoundFrame("${user.weight} kg", stringResource(R.string.weight))
            RoundFrame("${user.age}", stringResource(R.string.age))
        }
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Eat2FitButton(
                onClick = { onNavigateToRoute("${AppSections.PROFILE.route}/edit") },
            ) {
                Text(
                    text = stringResource(R.string.edit_profile),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
            Eat2FitButton(
                onClick = {
                    logoutDialog.value = true
                }

            )
            {
                Text(
                    text = stringResource(R.string.log_out),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Eat2FitDivider()
    }
}


@Composable
fun Notification_Section(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var notificationsEnabled by remember { mutableStateOf(false) }
    var showPermissionRequester = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        notificationsEnabled = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }




    if (showPermissionRequester.value) {
        PermissionRequester(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            rationale = stringResource(R.string.notifications_popup),
            permissionRequester = showPermissionRequester
        ) { isGranted ->
            notificationsEnabled = isGranted
            showPermissionRequester.value = false
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
                text = stringResource(R.string.notification_label),
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
                    stringResource(R.string.notifications_popup_label),
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
                                showPermissionRequester.value = true

                        } else {
                            coroutineScope.launch {
                                val intent =
                                    Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                    }
                                context.startActivity(intent)
                            }
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

    Text(stringResource(R.string.daily_progress),
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
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
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
                .padding(start = 8.dp, top = 24.dp, end = 8.dp, bottom = 24.dp)
        ) {

            Row(
                Modifier.fillMaxSize(),
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
                        text = stringResource(R.string.my_calories),
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
                        userGoal = user.goal!!
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        20.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = rememberAccessibility(),
                            tint = animateColorAsState(
                                Color(0xFFFF63F0),
                                label = stringResource(R.string.icon_label)
                            ).value,
                            contentDescription = stringResource(R.string.user_goal)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                4.dp,
                                alignment = Alignment.CenterVertically
                            ), horizontalAlignment = Alignment.End
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
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    }
                    if (user.goal != Goal.GAIN) { // Remaining section
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            Icon(
                                imageVector = rememberRemainingFood(),
                                tint = animateColorAsState(
                                    Color(0xFFFF63F0),
                                    label = stringResource(R.string.icon_label)
                                ).value,
                                contentDescription = stringResource(R.string.user_goal)
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    4.dp,
                                    alignment = Alignment.CenterVertically
                                ), horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = stringResource(R.string.remaining),
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontFamily = FontFamily(Font(R.font.karla_bold)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF121926),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                                if (availableCalories != null) {
                                    val remaining =
                                        calculateRemaining(availableCalories, currentCalories);
                                    val color = if (remaining == stringResource(R.string.overeating))
                                        Color(0xFFFF0101)
                                    else
                                        Color(0xFF121926)

                                    Text(
                                        text = remaining,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 28.sp,
                                            fontFamily = FontFamily(Font(R.font.karla_bold)),
                                            fontWeight = FontWeight(500),
                                            color = color,
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
    }
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
            contentDescription = null,
            user = user,
            modifier = Modifier.fillMaxSize()
        )
    }
}
