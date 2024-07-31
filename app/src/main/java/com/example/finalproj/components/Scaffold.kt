package com.example.finalproj.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.finalproj.ui.theme.Eat2FitTheme


@Composable
fun Eat2FitScaffold(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember {SnackbarHostState()},
    topBar: @Composable (() -> Unit) = {},
    bottomBar: @Composable (() -> Unit) = {},
    floatingActionButton: @Composable (() -> Unit) = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
//    isFloatingActionButtonDocked: Boolean = false,
//    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
//    drawerShape: Shape = MaterialTheme.shapes.large,
//    drawerElevation: Dp = DrawerDefaults.Elevation,
//    drawerBackgroundColor: Color = Eat2FitTheme.colors.uiBackground,
//    drawerContentColor: Color = Eat2FitTheme.colors.textSecondary,
//    drawerScrimColor: Color = Eat2FitTheme.colors.uiBorder,
//    backgroundColor: Color = Eat2FitTheme.colors.uiBackground,
    contentColor: Color = Eat2FitTheme.colors.textSecondary,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
//        isFloatingActionButtonDocked = isFloatingActionButtonDocked,
//        drawerContent = drawerContent,
//        drawerShape = drawerShape,
//        drawerElevation = drawerElevation,
//        drawerBackgroundColor = drawerBackgroundColor,
//        drawerContentColor = drawerContentColor,
//        drawerScrimColor = drawerScrimColor,
//        backgroundColor = backgroundColor,
        contentColor = contentColor,
        content = content
    )
}

