package com.example.finalproj.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.finalproj.R
import com.example.finalproj.components.DatePicker
import com.example.finalproj.components.DropDownMenu
import com.example.finalproj.components.GenericText
import com.example.finalproj.util.icons.rememberAccessibility
import com.example.finalproj.util.icons.rememberHeight
import com.example.finalproj.util.icons.rememberTransgender
import com.example.finalproj.util.icons.rememberWeight
import com.example.finalproj.util.validation.DropDownState
import com.example.finalproj.util.validation.TextStateString
import java.time.LocalDate

@Composable
fun ProfileDetails(
    genderState: DropDownState? = null,
    goalState: DropDownState? = null,
    heightState: TextStateString? = null,
    weightState: TextStateString? = null,
    pickedDate: MutableState<LocalDate>? = null
) {

    genderState?.let {
        DropDownMenu(
            genderState,
            options = listOf(
                stringResource(R.string.male),
                stringResource(R.string.female)
            ),
            icon = rememberTransgender()
        )
    }
    goalState?.let {
        DropDownMenu(
            goalState,
            options = listOf(
                stringResource(R.string.lose_weight),
                stringResource(R.string.maintain_weight),
                stringResource(R.string.gain_weight)
            ),
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
            label = stringResource(R.string.your_height),
            icon = rememberHeight(),
            textState = heightState
        )
    }
    weightState?.let {
        GenericText(
            isOutlined = true,
            label = stringResource(R.string.your_weight),
            icon = rememberWeight(),
            textState = weightState
        )
    }
}