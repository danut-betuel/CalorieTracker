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
import com.betuel.calorietracker.ui.theme.CalorieTrackerTheme
import com.betuel.core.domain.preferences.Preferences
import com.betuel.calorietracker.navigation.Route
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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()

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
                        startDestination = if (shouldShowOnboarding) {
                            Route.WELCOME
                        } else Route.TRACKER_OVERVIEW,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = paddingValues.calculateBottomPadding())
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNextClick = {
                                navController.navigate(Route.GENDER)
                            })
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNextClick = {
                                navController.navigate(Route.AGE)
                            })
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.HEIGHT)
                                }
                            )
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.WEIGHT)
                                }
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.ACTIVITY_LEVEL)
                                }
                            )
                        }
                        composable(Route.ACTIVITY_LEVEL) {
                            ActivityLevelScreen(onNextClick = {
                                navController.navigate(Route.GOAL)
                            })
                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNextClick = {
                                navController.navigate(Route.NUTRIENT_GOAL)
                            })
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.TRACKER_OVERVIEW)
                                }
                            )
                        }

                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigateToSearch = { mealName, day, month, year ->
                                navController.navigate(
                                    Route.SEARCH + "/$mealName" +
                                            "/$day" +
                                            "/$month" +
                                            "/$year"
                                )
                            }
                            )
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
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