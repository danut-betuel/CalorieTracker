package com.betuel.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.betuel.calorietracker.navigation.navigate
import com.betuel.calorietracker.ui.theme.CalorieTrackerTheme
import com.betuel.core.navigation.Route
import com.betuel.onboarding_presentation.activity.ActivityLevelScreen
import com.betuel.onboarding_presentation.gender.AgeScreen
import com.betuel.onboarding_presentation.gender.GenderScreen
import com.betuel.onboarding_presentation.goal.GoalScreen
import com.betuel.onboarding_presentation.height.HeightScreen
import com.betuel.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.betuel.onboarding_presentation.weight.WeightScreen
import com.betuel.onboarding_presentation.welcome.WelcomeScreen
import com.betuel.tracker_presentation.search.SearchScreen
import com.betuel.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.WELCOME,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = paddingValues.calculateBottomPadding())
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.ACTIVITY_LEVEL) {
                            ActivityLevelScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigate = navController::navigate)
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName"){
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth"){
                                    type = NavType.IntType
                                },
                                navArgument("month"){
                                    type = NavType.IntType
                                },
                                navArgument("year"){
                                    type = NavType.IntType
                                }
                            )
                        ) { bacStackEntry ->
                            val mealName = bacStackEntry.arguments?.getString("mealName")!!
                            val dayOfMonth = bacStackEntry.arguments?.getInt("dayOfMonth")!!
                            val month = bacStackEntry.arguments?.getInt("month")!!
                            val year = bacStackEntry.arguments?.getInt("year")!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = navController::navigateUp
                            )
                        }
                    }
                }
            }
        }
    }
}