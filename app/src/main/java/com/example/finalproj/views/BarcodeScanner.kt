package com.example.finalproj.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.util.BarcodeScanner
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproj.components.BottomNavigationMenu
import com.example.finalproj.components.Eat2FitScaffold

@Composable
fun BarcodeScan(
    popBack: () -> Boolean,
    onSuccessfulScan: (String) -> Unit,
    onNavigateToRoute: (String) -> Unit
) {

    val context = LocalContext.current
    val barcodeScanner = BarcodeScanner(context)
    val scope = rememberCoroutineScope()
    val barcodeResults by barcodeScanner.barCodeResults.collectAsStateWithLifecycle()


    LaunchedEffect(barcodeResults) {
        barcodeResults?.let {
            onSuccessfulScan(it)
        }
    }


    Eat2FitScaffold(
        bottomBar = {
            BottomNavigationMenu(
                tabs = AppSections.values(),
                currentRoute = AppSections.BARCODE_SCAN.route,
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

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                LaunchedEffect(Unit) {
                    scope.launch {
                        barcodeScanner.startScan(popBack)
                    }
                }
            }
        }
    }
}