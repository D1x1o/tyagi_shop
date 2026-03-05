package com.example.tyagi_shop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tyagi_shop.data.UserSession
import com.example.tyagi_shop.ui.theme.MosyaginTheme
import com.example.tyagi_shop.ui.view.CatalogScreen
import com.example.tyagi_shop.ui.view.DetailsScreen
import com.example.tyagi_shop.ui.view.FavoriteScreen
import com.example.tyagi_shop.ui.view.ForgotPasswordScreen
import com.example.tyagi_shop.ui.view.HomeScreen
import com.example.tyagi_shop.ui.view.NewPasswordScreen
import com.example.tyagi_shop.ui.view.Onboard1Screen
import com.example.tyagi_shop.ui.view.Onboard2Screen
import com.example.tyagi_shop.ui.view.Onboard3Screen
import com.example.tyagi_shop.ui.view.ProfileScreen
import com.example.tyagi_shop.ui.view.VerifyOTPScreen
import com.example.upsidorkin.ui.view.LoginScreen
import com.example.upsidorkin.ui.view.RegisterScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MosyaginTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("onboard1") { Onboard1Screen(navController) }
                        composable("onboard2") { Onboard2Screen(navController) }
                        composable("onboard3") { Onboard3Screen(navController) }
                        composable("login") { LoginScreen(navController = navController) }
                        composable("home") { HomeScreen(navController = navController) }
                        composable("register") { RegisterScreen(navController = navController) }
                            composable(
                                route = "verifyOTP/{email}/{type}",
                                arguments = listOf(
                                    navArgument("email") { type = NavType.StringType },
                                    navArgument("type") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                val email = backStackEntry.arguments?.getString("email") ?: ""
                                val type = backStackEntry.arguments?.getString("type") ?: "signup"
                                VerifyOTPScreen(
                                    navController = navController,
                                    email = email,
                                    otpType = type
                                )}
                        composable("forgot_password") {
                            ForgotPasswordScreen(navController)
                        }
                        composable(
                            route = "new_password/{email}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            NewPasswordScreen(navController = navController, email = email)
                        }
                        composable("profile") {
                            val userId = UserSession.userId
                            val accessToken = UserSession.accessToken
                            Log.d("Nav", "UId: $userId")
                            Log.d("Nav", "AccessToken: $accessToken")
                            if (userId != null && accessToken != null) {
                                ProfileScreen(
                                    navController = navController,
                                    userId = userId,
                                    accessToken = accessToken
                                )
                            } else {
                                LoginScreen(navController = navController)
                            }
                        }
                        composable("favorite") {
                            FavoriteScreen(navController = navController)
                        }
                        composable(
                            route = "catalog/{category}",
                            arguments = listOf(
                                navArgument("category") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val category =
                                backStackEntry.arguments?.getString("category") ?: "Outdoor"
                            CatalogScreen(
                                navController = navController,
                                initialCategoryTitle = category
                            )
                        }
                        composable("catalog") {
                            CatalogScreen(
                                navController = navController,
                                initialCategoryTitle = "Outdoor"
                            )
                        }
                        composable(
                            route = "details/{productId}",
                            arguments = listOf(
                                navArgument("productId") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString("productId") ?: ""
                            DetailsScreen(
                                navController = navController,
                                productId = productId
                            )
                        }
                    }
                }
            }
        }
    }
}

