package com.betuel.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.betuel.calorietracker.navigation.RootNavGraph
import com.betuel.calorietracker.ui.theme.CalorieTrackerTheme
import com.betuel.core.domain.preferences.Preferences
import com.betuel.onboarding_presentation.OnOnboardingFinished
import com.betuel.onboarding_presentation.OnboardingNavGraph
import com.betuel.tracker_presentation.TrackerNavGraph
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import org.koin.android.ext.android.inject
import com.ramcosta.composedestinations.navigation.navigate

class MainActivity : ComponentActivity() {

    private val preferences: Preferences by inject()

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
                    DestinationsNavHost(
                        navGraph = RootNavGraph,
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = paddingValues.calculateBottomPadding()),
                        startRoute = if (shouldShowOnboarding) OnboardingNavGraph else TrackerNavGraph,
                        dependenciesContainerBuilder = {
                            dependency(scaffoldState)
                            dependency(OnboardingNavGraph) {
                                OnOnboardingFinished { navController.navigate(TrackerNavGraph) }
                            }
                        }
                    )
                }
            }
        }
    }
}