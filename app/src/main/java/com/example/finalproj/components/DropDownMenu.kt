package com.example.finalproj.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.example.finalproj.ui.theme.Shapes
import com.example.finalproj.util.validation.DropDownState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    textState: TextFieldState = remember { DropDownState() },
    options: List<String>,
    icon: ImageVector,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    ) {

    var expanded by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {


        OutlinedTextField(
            supportingText = {
                textState.getError()?.let { error -> TextFieldError(textError = error) }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            singleLine = true,
            shape = Shapes.small,
            visualTransformation = VisualTransformation.None,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = textState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.menuAnchor().fillMaxWidth()
                .onFocusChanged { focusState ->
                    textState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        textState.enableShowErrors()
                    }
                },
            leadingIcon = {
                Icon(imageVector = icon, contentDescription = null)

            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            value = textState.text,
            readOnly = true,
            onValueChange = {
                textState.text = it
            },
        )


        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    colors = MenuDefaults.itemColors(),
                    modifier = Modifier.fillMaxSize(),
                    text = { Text(text = selectionOption) },
                    onClick = {
                        textState.text = selectionOption
                        expanded = false
                    }
                )
            }
        }

    }
}