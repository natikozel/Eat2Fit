package com.example.finalproj.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.finalproj.R
import com.example.finalproj.components.BottomNavigationMenu
import com.example.finalproj.components.Eat2FitScaffold
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.models.User

@Composable
fun Search(
    onMealClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToRoute: (String) -> Unit
) {
//
//
//    var user by remember { mutableStateOf<User?>(null) }
//    var isLoading by remember { mutableStateOf(true) }
//    val userId = AuthenticationManager.getCurrentUser()?.uid
//
//    LaunchedEffect(userId) {
//        userId?.let {
//            DatabaseManager.readUser(userId)
//                .addOnSuccessListener { dataSnapshot ->
//                    user = dataSnapshot.getValue(User::class.java)
//                    isLoading = false
//                }
//                .addOnFailureListener { exception ->
//                    // Handle error
//                    isLoading = false
//                }
//        }
//    }
//
//
//
//    if (isLoading) {
//        // Show a loading indicator while the user data is being fetched
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//    } else {
//        user?.let {

            Eat2FitScaffold(
                bottomBar = {
                    BottomNavigationMenu(
                        tabs = AppSections.values(),
                        currentRoute = "search",
                        navigateToRoute = onNavigateToRoute
                    )
                }
            )
            {




//                Box(
//                    Modifier
//                        .fillMaxSize()
//                ) {
//                    val scroll = rememberScrollState(0)
//                    Header()
//                    Body(scroll, user!!)
//                    Title(onNavigateToRoute = onNavigateToRoute, user = user!!) { scroll.value }
//                    Image(R.drawable.profiledemo) { scroll.value }
//
//
//                }




//
//            }
//
//        } ?: run {
//            // Show an error message if the user data could not be fetched
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                // Error message
//            }
//        }
//
    }
}