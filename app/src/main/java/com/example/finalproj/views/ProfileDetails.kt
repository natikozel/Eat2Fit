package com.example.finalproj.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finalproj.components.DatePicker
import com.example.finalproj.components.DropDownMenu
import com.example.finalproj.components.GenericText
import com.example.finalproj.util.icons.rememberAccessibility
import com.example.finalproj.util.icons.rememberHeight
import com.example.finalproj.util.icons.rememberTransgender
import com.example.finalproj.util.icons.rememberWeight
import com.example.finalproj.util.validation.TextState
import java.time.LocalDate

@Composable
fun ProfileDetails(
    genderState: TextState? = null,
    goalState: TextState? = null,
    heightState: TextState? = null,
    weightState: TextState? = null,
    pickedDate: MutableState<LocalDate>? = null
) {

    genderState?.let {
        DropDownMenu(
            genderState,
            options = listOf("Male", "Female"),
            icon = rememberTransgender()
        )
    }
    goalState?.let {
        DropDownMenu(
            goalState,
            options = listOf("Lose Weight", "Maintain Weight", "Gain Weight"),
            icon = rememberAccessibility()
        )
    }
    pickedDate?.let {
        DatePicker(pickedDate)
        Spacer(Modifier.height(4.dp))
    }
    heightState?.let {
        GenericText(
            isOutlined = true,
            label = "Your Height",
            icon = rememberHeight(),
            textState = heightState
        )
    }
    weightState?.let {
        GenericText(
            isOutlined = true,
            label = "Your Weight",
            icon = rememberWeight(),
            textState = weightState
        )
    }


}