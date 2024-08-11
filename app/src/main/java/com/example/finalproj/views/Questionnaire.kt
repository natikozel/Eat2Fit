package com.example.finalproj.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.finalproj.models.Gender
import com.example.finalproj.models.Goal
import com.example.finalproj.models.User
import com.example.finalproj.util.Destinations
import com.example.finalproj.util.dailyCaloriesConsumption
import com.example.finalproj.util.validation.DropDownState
import com.example.finalproj.util.validation.TextStateString
import java.time.LocalDate
import java.util.Locale


private fun Context.doSubmit(
    onNavigateToRoute: (String) -> Unit,
    gender: String,
    goal: String,
    height: String,
    weight: String,
    date: LocalDate,
) {
    AuthenticationManager.getCurrentUser()?.let { user ->
        DatabaseManager.readUser().addOnSuccessListener { dataSnapshot ->
            val returnedUser = dataSnapshot.getValue(User::class.java)
            returnedUser?.gender =
                Gender.entries.first { it.name == gender.uppercase(Locale.getDefault()) }
            returnedUser?.goal =
                Goal.entries.first { goal.uppercase(Locale.getDefault()).contains(it.name)  }
            returnedUser?.height = height.toDouble()
            returnedUser?.weight = weight.toDouble()
            returnedUser?.age = LocalDate.now().year - date.year
            returnedUser?.maxCalories = dailyCaloriesConsumption(
                returnedUser?.weight!!,
                returnedUser?.height!!,
                returnedUser?.age!!,
                returnedUser?.gender!!
            ).toInt()
            returnedUser?.hasLoggedInOnce = true
            if (returnedUser != null) {
                DatabaseManager.updateUser(returnedUser)
                onNavigateToRoute(Destinations.PROFILE)
            }
        }
    }
}

@Composable
fun Questionnaire(
    onNavigateToRoute: (String) -> Unit,
    popBack: () -> Boolean,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {

    val context = LocalContext.current
    val genderState = remember {
        DropDownState(input = "Choose Gender")
    }
    val goalState = remember {
        DropDownState(input = "Your Goal")
    }

    val heightState = remember {
        TextStateString()
    }

    val weightState = remember {
        TextStateString()
    }

    val pickedDate = remember { mutableStateOf(LocalDate.now()) }
    Eat2FitSurface(
        elevation = elevation,
        modifier = modifier
    ) {
        Column(
            Modifier
                .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState(), reverseScrolling = true),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
        ) {
            NavigateBackArrow(popBack)
            Column {
                Image(
                    modifier = Modifier
                        .size(width = 100.dp, height = 120.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.logo_image),
                    contentScale = ContentScale.FillBounds
                )
            }



            ProfileDetails(
                genderState = genderState,
                goalState = goalState,
                heightState = heightState,
                weightState = weightState,
                pickedDate = pickedDate
            )



            Spacer(Modifier.height(8.dp))

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 35.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = stringResource(R.string.complete_profile),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 30.sp,
                        fontFamily = FontFamily(Font(R.font.karla_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF1D1617),
                    )
                )
                Text(
                    text = stringResource(R.string.know_more_about_you),

                    // Text / Small Text (Regular)
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.karla_bold)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF7B6F72),
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(Modifier.height(8.dp))


                Eat2FitButton(
                    modifier = Modifier
                        .shadow(
                            elevation = 22.dp,
                            spotColor = Color(0x4D95ADFE),
                            ambientColor = Color(0x4D95ADFE)
                        ),
                    enabled =
                        genderState.isValid &&
                            goalState.isValid &&
                            heightState.isValid &&
                            weightState.isValid &&
                            pickedDate.value.year < LocalDate.now().year
                    ,
                    onClick = {
                        if (genderState.isValid &&
                            goalState.isValid &&
                            heightState.isValid &&
                            weightState.isValid &&
                            pickedDate.value.year < LocalDate.now().year)
                            context.doSubmit(
                                onNavigateToRoute,
                                genderState.text,
                                goalState.text,
                                heightState.text,
                                weightState.text,
                                pickedDate.value,
                            )
                    }) {
                    Column(
                        Modifier.size(width = 315.dp, height = 60.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp, alignment = Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            fontSize = 20.sp,
                            text = stringResource(R.string.next),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
