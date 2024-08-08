package com.example.finalproj.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun PermissionRequester(
    permission: String,
    rationale: String,
    onPermissionResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var showRationale by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        onPermissionResult(isGranted)
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionResult(true)
            }
            shouldShowRequestPermissionRationale(context, permission) -> {
                showRationale = true
            }
            else -> {
                launcher.launch(permission)
            }
        }
    }

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text(text = "Permission Required") },
            text = { Text(rationale) },
            confirmButton = {
                Button(onClick = {
                    showRationale = false
                    launcher.launch(permission)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showRationale = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun shouldShowRequestPermissionRationale(context: Context, permission: String): Boolean {
    return androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(
        context as android.app.Activity,
        permission
    )
}