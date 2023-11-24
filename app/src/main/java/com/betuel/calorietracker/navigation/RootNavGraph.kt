package com.betuel.calorietracker.navigation

import com.betuel.onboarding_presentation.OnboardingNavGraph
import com.betuel.tracker_presentation.TrackerNavGraph
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

object RootNavGraph : NavGraphSpec {

    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = OnboardingNavGraph

    override val nestedNavGraphs = listOf(
        OnboardingNavGraph,
        TrackerNavGraph
    )
}