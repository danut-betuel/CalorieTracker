package com.betuel.tracker_domain.di

import com.betuel.core.domain.preferences.Preferences
import com.betuel.tracker_domain.repository.TrackerRepository
import com.betuel.tracker_domain.use_case.CalculateMealNutrients
import com.betuel.tracker_domain.use_case.DeleteTrackFood
import com.betuel.tracker_domain.use_case.GetsFoodsForDate
import com.betuel.tracker_domain.use_case.SearchFood
import com.betuel.tracker_domain.use_case.TrackFood
import com.betuel.tracker_domain.use_case.TrackerUseCases
import org.koin.dsl.module

val trackerDomainModule = module {
    single { provideTrackerUseCases(get(), get()) }
}

fun provideTrackerUseCases(
    repository: TrackerRepository,
    preferences: Preferences
): TrackerUseCases {
    return TrackerUseCases(
        trackFood = TrackFood(repository),
        searchFood = SearchFood(repository),
        getsFoodsForDate = GetsFoodsForDate(repository),
        deleteTrackedFood = DeleteTrackFood(repository),
        calculateMealNutrients = CalculateMealNutrients(preferences)
    )
}