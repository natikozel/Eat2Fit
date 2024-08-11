package com.example.finalproj.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproj.components.Eat2FitScaffold
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.components.HeaderLogo
import com.example.finalproj.components.NavigateBackArrow
import com.example.finalproj.database.models.FoodViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.finalproj.database.models.FoodDetails
import com.example.finalproj.R
import com.example.finalproj.components.LoadingComposable
import com.example.finalproj.util.navigateToProfile
import java.util.Locale

@Composable
fun BarcodeScanResultScreen(
    barcode: String,
    viewModel: FoodViewModel = viewModel(),
    navigateAndClear: (String) -> Unit
) {
    val foodDetails by viewModel.foodDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(barcode) {
        viewModel.fetchFoodDetails(barcode)
    }


    Eat2FitScaffold(
        topBar = { HeaderLogo() }
    )
    { paddingValues ->
        Eat2FitSurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState(), reverseScrolling = false),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                NavigateBackArrow { navigateToProfile(navigateAndClear) }

                if (isLoading) {
                    LoadingComposable()
                } else {
                    foodDetails?.let {
                        FoodDetails(it)
                    } ?: NoResults()
                }
            }
        }
    }
}

@Composable
fun FoodDetails(foodDetails: FoodDetails) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = foodDetails.product_name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        foodDetails.image_url.let {
            if (it.isEmpty() || it == "") {
                Text("No image available for this item")
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(foodDetails.image_url)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
        Text(
            text = "Calories per 100g: ${
                foodDetails.energy.let {
                    if (it.isEmpty()) "0" else String.format(Locale.getDefault(), "%.2f", it.toFloat())
                }
            }",
            fontSize = 18.sp,
        )

        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painterResource(id = R.drawable.qrcode),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
//                    .fillMaxSize()
                    .size(height = 200.dp, width = 200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.Center)
            )
            Image(
                painterResource(id = R.drawable.scanned),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
fun NoResults() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(40.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = stringResource(R.string.barcode_fail),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.karla_bold)),
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painterResource(id = R.drawable.qrcode),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
//                    .fillMaxSize()
                    .size(height = 200.dp, width = 200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.Center)
            )
            Image(
                painterResource(id = R.drawable.failed),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}
