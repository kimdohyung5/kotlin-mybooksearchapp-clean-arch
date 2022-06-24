package com.kimdo.mybooksearchapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.work.WorkManager
import com.kimdo.mybooksearchapp.data.api.BookSearchApi
import com.kimdo.mybooksearchapp.data.db.BookSearchDatabase
import com.kimdo.mybooksearchapp.util.Constants.BASE_URL
import com.kimdo.mybooksearchapp.util.Constants.DATASTORE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingIntercepter = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor( httpLoggingIntercepter )
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BookSearchApi {
        return retrofit.create(BookSearchApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBookSearchDatabase(@ApplicationContext context: Context): BookSearchDatabase =
        Room.databaseBuilder(context.applicationContext,
        BookSearchDatabase::class.java,
        "favorite-books")
            .build()

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context) : DataStore<Preferences> =
        PreferenceDataStoreFactory.create  (
            produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME)}
                )

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)

    @Singleton
    @Provides
    fun provideCacheDeleteResult(): String = "Cache has deleted by Hilt"
}