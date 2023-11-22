package com.betuel.onboarding_domain.di

import com.betuel.onboarding_domain.use_case.ValidateNutrients
import org.koin.dsl.module

val onboardingDomainModule = module {
    single { ValidateNutrients() }
}