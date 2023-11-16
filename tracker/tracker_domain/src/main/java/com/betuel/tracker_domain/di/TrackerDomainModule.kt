package com.betuel.tracker_domain.di

import com.betuel.core.domain.preferences.Preferences
import com.betuel.tracker_domain.repository.TrackerRepository
import com.betuel.tracker_domain.use_case.CalculateMealNutrients
import com.betuel.tracker_domain.use_case.DeleteTrackFood
import com.betuel.tracker_domain.use_case.GetsFoodsForDate
import com.betuel.tracker_domain.use_case.SearchFood
import com.betuel.tracker_domain.use_case.TrackFood
import com.betuel.tracker_domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getsFoodsForDate = GetsFoodsForDate(repository),
            deleteTrackFood = DeleteTrackFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )
    }
}