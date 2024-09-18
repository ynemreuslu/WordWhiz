package com.ynemreuslu.wordwhiz.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.mlkit.common.model.RemoteModelManager
import com.ynemreuslu.wordwhiz.data.SharedPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourcesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("learned_words_prefs", Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesDataSource(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): SharedPreferencesDataSource {
        return SharedPreferencesDataSource(sharedPreferences, gson)
    }

    @Provides
    fun provideRemoteModelManager(): RemoteModelManager {
        return RemoteModelManager.getInstance()

    }
}