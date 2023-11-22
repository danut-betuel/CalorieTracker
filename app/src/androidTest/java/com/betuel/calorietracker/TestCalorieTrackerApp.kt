package com.betuel.calorietracker

import android.app.Application
import com.betuel.calorietracker.di.appModule
import com.betuel.onboarding_domain.di.onboardingDomainModule
import com.betuel.onboarding_presentation.di.onboardingPresentationModule
import com.betuel.tracker_data.di.trackerDataModule
import com.betuel.tracker_domain.di.trackerDomainModule
import com.betuel.tracker_presentation.di.trackerPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TestCalorieTrackerApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TestCalorieTrackerApp)
            androidLogger(Level.NONE)
            modules(
                listOf(
                    appModule,
                    onboardingDomainModule,
                    onboardingPresentationModule,
                    trackerDataModule,
                    trackerDomainModule,
                    trackerPresentationModule
                )
            )
        }
    }
}