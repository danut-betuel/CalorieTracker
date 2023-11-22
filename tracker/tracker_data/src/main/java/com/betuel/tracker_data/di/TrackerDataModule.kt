package com.betuel.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.betuel.tracker_data.local.TrackerDatabase
import com.betuel.tracker_data.remote.OpenFoodApi
import com.betuel.tracker_data.repository.TrackerRepositoryImpl
import com.betuel.tracker_domain.repository.TrackerRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


val trackerDataModule = module {
    single { provideOkHttpClient() }
    single { provideOpenFoodApi(get()) }
    single { provideTrackerDatabase(androidApplication()) }
    single { provideTrackerRepository(get(), get()) }
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
}

fun provideOpenFoodApi(client: OkHttpClient): OpenFoodApi {
    return Retrofit.Builder()
        .baseUrl(OpenFoodApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
        .create()
}

fun provideTrackerDatabase(app: Application): TrackerDatabase {
    return Room.databaseBuilder(
        app,
        TrackerDatabase::class.java,
        "tracker_db"
    ).build()
}

fun provideTrackerRepository(
    api: OpenFoodApi,
    db: TrackerDatabase
): TrackerRepository {
    return TrackerRepositoryImpl(
        dao = db.dao,
        api = api
    )
}