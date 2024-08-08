package com.example.finalproj.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.finalproj.ui.theme.BorderColor
import com.example.finalproj.ui.theme.BrandColor
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.ui.theme.Shapes
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.Locale

@Composable
fun DatePicker(
    pickedDate: MutableState<LocalDate> = mutableStateOf(LocalDate.now()),
    imeAction: ImeAction = ImeAction.Next,
) {

    val dateDialogState = rememberMaterialDialogState()

    TextButton(
        contentPadding = PaddingValues(0.dp),
        elevation = null,
        border = BorderStroke(1.dp, BorderColor),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent,

            ),
        onClick = {
            dateDialogState.show()
        }) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = Shapes.small,
            visualTransformation = VisualTransformation.None,
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = KeyboardType.Email
            ),
            value = pickedDate.value.toString(),
            enabled = false,
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.DateRange, contentDescription = null)
            },
            readOnly = true,
            onValueChange = {
            },
            )


        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Ok")
                negativeButton(text = "Cancel")
            }
        ) {

            datepicker(
                locale = Locale.ENGLISH,
                waitForPositiveButton = true,
                allowedDateValidator = { date ->
                    date.isBefore(LocalDate.now())
                },
                initialDate = LocalDate.now(),
                title = "Pick a date",
            ) {
                pickedDate.value = it
            }

        }

    }
}