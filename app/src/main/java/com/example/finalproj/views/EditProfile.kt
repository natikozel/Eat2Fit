package com.example.finalproj.views

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproj.R
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.components.NavigateBackArrow
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.models.Goal
import com.example.finalproj.models.User
import com.example.finalproj.util.dailyCaloriesConsumption
import com.example.finalproj.util.validation.DropDownState
import com.example.finalproj.util.validation.TextStateString
import java.util.Locale

@Composable
fun EditProfile(
    popBack: () -> Boolean,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    val goalState = remember {
        DropDownState(input = "Your Goal")
    }

    val heightState = remember {
        TextStateString()
    }

    val weightState = remember {
        TextStateString()
    }

    val context = LocalContext.current
    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        DatabaseManager.readUser().addOnSuccessListener { dataSnapshot ->
            user = dataSnapshot.getValue(User::class.java)
            goalState.text = "${
                user?.goal.toString().lowercase(Locale.ROOT).replaceFirstChar { it.uppercase() }
            } Weight"
            heightState.text = user?.height.toString()
            weightState.text = user?.weight.toString()
            isLoading = false
        }
            .addOnFailureListener {
                isLoading = false
            }

    }

    if (isLoading) {
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
                    NavigateBackArrow(popBack)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        Spacer(Modifier.height(40.dp))
                        Text(
                            text = stringResource(R.string.edit_default_label),
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
                                    user?.previousWeights!!,
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
                                    text = stringResource(R.string.continue_label),
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.not_found))
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
    previousWeightList: List<Double>,
) {

    val newWeightList = previousWeightList.toMutableList()
    newWeightList.add(oldWeight)
    DatabaseManager.readUser().addOnSuccessListener { dataSnapshot ->
        val returnedUser = dataSnapshot.getValue(User::class.java)
        returnedUser?.goal =
            Goal.entries.first { newGoal.uppercase(Locale.getDefault()).contains(it.name) }
        returnedUser?.height = newHeight.toDouble()
        returnedUser?.weight = newWeight.toDouble()
        returnedUser?.maxCalories = dailyCaloriesConsumption(
            returnedUser?.weight!!,
            returnedUser?.height!!,
            returnedUser?.age!!,
            returnedUser.gender!!
        ).toInt()
        returnedUser.previousWeights = newWeightList.toList()
        AuthenticationManager.setUser(returnedUser)
        DatabaseManager.updateUser(returnedUser)
        popBack();
    }
}