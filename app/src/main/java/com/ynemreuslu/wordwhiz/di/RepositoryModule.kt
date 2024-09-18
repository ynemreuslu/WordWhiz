package com.ynemreuslu.wordwhiz.di

import android.content.Context
import com.ynemreuslu.wordwhiz.data.SharedPreferencesDataSource
import com.ynemreuslu.wordwhiz.data.WordRepository
import com.ynemreuslu.wordwhiz.data.network.WordDictionaryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWordRepository(
        sharedPreferencesDataSource: SharedPreferencesDataSource,
        @ApplicationContext context: Context,
        wordDictionaryApi: WordDictionaryApi
    ): WordRepository {
        return WordRepository(sharedPreferencesDataSource, context, wordDictionaryApi)
    }
}