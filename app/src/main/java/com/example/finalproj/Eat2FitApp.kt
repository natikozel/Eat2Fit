package com.example.finalproj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.models.User
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.views.Landing
import com.example.finalproj.views.Navigation
import com.google.firebase.Firebase
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.database


class Eat2FitApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        DatabaseManager.initDb()
        setContent { Eat2Fit() }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }

    }
}


@Composable
fun Eat2Fit() {
    Eat2FitTheme {
        Navigation()
    }
}
