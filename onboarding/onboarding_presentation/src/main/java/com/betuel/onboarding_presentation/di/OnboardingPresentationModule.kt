package com.betuel.onboarding_presentation.di

import com.betuel.onboarding_presentation.activity.ActivityLevelViewModel
import com.betuel.onboarding_presentation.age.AgeViewModel
import com.betuel.onboarding_presentation.gender.GenderViewModel
import com.betuel.onboarding_presentation.goal.GoalViewModel
import com.betuel.onboarding_presentation.height.HeightViewModel
import com.betuel.onboarding_presentation.nutrient_goal.NutrientGoalViewModel
import com.betuel.onboarding_presentation.weight.WeightViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {
    viewModel { ActivityLevelViewModel(get()) }
    viewModel { AgeViewModel(get(), get()) }
    viewModel { GenderViewModel(get()) }
    viewModel { GoalViewModel(get()) }
    viewModel { HeightViewModel(get(), get()) }
    viewModel { NutrientGoalViewModel(get(), get(), get()) }
    viewModel { WeightViewModel(get()) }
}