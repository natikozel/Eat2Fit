package com.example.finalproj.views

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.finalproj.R
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.components.NavigateBack
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.DatabaseKeys
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.models.Goal
import com.example.finalproj.database.models.User
import com.example.finalproj.util.validation.TextState
import java.time.LocalDate
import java.util.Locale

@Composable
fun EditProfile(
    popBack: () -> Boolean,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {

    val goalState = remember {
        TextState(input = "Your Goal")
    }

    val heightState = remember {
        TextState()
    }

    val weightState = remember {
        TextState()
    }

    val context = LocalContext.current
    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val userId = AuthenticationManager.getCurrentUser()?.uid

    LaunchedEffect(userId) {
        userId?.let {
            DatabaseManager.readUser(userId).addOnSuccessListener { dataSnapshot ->
                user = dataSnapshot.getValue(User::class.java)
                goalState.text = "${
                    user?.goal.toString().lowercase(Locale.ROOT).replaceFirstChar { it.uppercase() }
                } Weight"
                heightState.text = user?.height.toString()
                weightState.text = user?.weight.toString()
                isLoading = false
            }
                .addOnFailureListener { exception ->
                    // Handle error
                    isLoading = false
                }
        }
    }

    if (isLoading) {
        // Show a loading indicator while the user data is being fetched
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        user?.let {
            if (user?.previousWeights == null) {
                user?.previousWeights = emptyList()
            }

            Eat2FitSurface(
                elevation = elevation,
                modifier = modifier
            ) {
                Column(
                    Modifier
                        .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                        .verticalScroll(rememberScrollState(), reverseScrolling = true)
                        .navigationBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        8.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    NavigateBack(popBack)

                    Column(
//                modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        Spacer(Modifier.height(40.dp))
                        Text(
                            text = "Please update some details to continue",

                            // Title/H4 (Bold)
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 30.sp,
                                fontFamily = FontFamily(Font(R.font.karla_bold)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF1D1617),
                            )
                        )
                        Spacer(Modifier.height(80.dp))

                        ProfileDetails(
                            goalState = goalState,
                            heightState = heightState,
                            weightState = weightState,
                        )
                        Spacer(Modifier.height(40.dp))

                        Eat2FitButton(
                            enabled = goalState.isValid && heightState.isValid && weightState.isValid,
                            modifier = Modifier
                                .shadow(
                                    elevation = 22.dp,
                                    spotColor = Color(0x4D95ADFE),
                                    ambientColor = Color(0x4D95ADFE)
                                ),
                            onClick = {
                                context.doEdit(
                                    popBack,
                                    goalState.text,
                                    heightState.text,
                                    weightState.text,
                                    user?.weight!!,
                                    user?.previousWeights!!
                                )
                            }) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp, alignment = Alignment.CenterVertically
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    fontSize = 14.sp,
                                    text = "Continue",
                                    modifier = Modifier.width(100.dp),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                            }

                        }
                    }
                }
            }
        } ?: run {
            // Handle the case where user data is null
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "User not found")
            }
        }
    }
}

private fun Context.doEdit(
    popBack: () -> Boolean,
    newGoal: String,
    newHeight: String,
    newWeight: String,
    oldWeight: Double,
    previousWeight: List<Double>
) {

    val newWeightList = previousWeight.toMutableList()
    newWeightList.add(oldWeight)

    DatabaseManager.updateUserKey(
        DatabaseKeys.GOAL,
        Goal.entries.first { newGoal.uppercase(Locale.getDefault()).contains(it.name) })
    DatabaseManager.updateUserKey(DatabaseKeys.HEIGHT, newHeight.toDouble())
    DatabaseManager.updateUserKey(DatabaseKeys.WEIGHT, newWeight.toDouble())
    DatabaseManager.updateUserKey(DatabaseKeys.PREVIOUS_WEIGHTS, newWeightList.toList())
    popBack();
}