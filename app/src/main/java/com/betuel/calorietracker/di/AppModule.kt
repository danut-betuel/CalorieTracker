package com.betuel.calorietracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.betuel.core.data.preferences.DefaultPreferences
import com.betuel.core.domain.preferences.Preferences
import com.betuel.core.domain.use_case.FilterOutDigits
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val appModule = module {
    single { provideSharedPreferences(androidApplication()) }
    single<Preferences> { DefaultPreferences(get()) }
    single { FilterOutDigits() }
}

fun provideSharedPreferences(app: Application): SharedPreferences {
    return app.getSharedPreferences("shared_pref", MODE_PRIVATE)
}