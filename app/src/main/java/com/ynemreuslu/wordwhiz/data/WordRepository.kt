package com.ynemreuslu.wordwhiz.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ynemreuslu.wordwhiz.data.model.Words
import com.ynemreuslu.wordwhiz.data.network.WordDictionaryApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val sharedPreferencesDataSource: SharedPreferencesDataSource,
    @ApplicationContext private val context: Context,
    private val apiService: WordDictionaryApi
) {

    fun saveJsonDataToSharedPrefsIfFirstTime() {
        val isFirstRun = sharedPreferencesDataSource.isFirstRun()
        if (isFirstRun) {
            val json = context.assets.open("words.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<Words>>() {}.type
            val wordList: List<Words> = Gson().fromJson(json, type)
            sharedPreferencesDataSource.saveWordsToSharedPreferences(wordList)
            sharedPreferencesDataSource.setFirstRunCompleted()
        }
    }


    fun getWords(): List<Words> {
        return sharedPreferencesDataSource.getWordsFromSharedPreferences()
    }

    fun addWord(word: Words) {
        sharedPreferencesDataSource.addWordToSharedPreferences(word)
    }

    fun removeWord(word: Words) {
        sharedPreferencesDataSource.removeWordFromSharedPreferences(word)
    }

    fun shuffleWords(): List<Words> {
        return sharedPreferencesDataSource.shuffleWordsAndSave()
    }


    fun getLearnedWords(): List<Words> {
        return sharedPreferencesDataSource.getLearnedWords()
    }

    fun addWordToLearnedList(word: Words) {
        sharedPreferencesDataSource.addLearnedWord(word)
    }

    fun removeWordFromLearnedList(word: Words) {
        sharedPreferencesDataSource.removeLearnedWord(word)
    }

    fun isWordInLearnedList(word: Words): Boolean {
        return sharedPreferencesDataSource.isWordInLearnedList(word)
    }

    fun isLearningListEmpty(): Boolean {
        return sharedPreferencesDataSource.isLearningListEmpty()
    }


    suspend fun fetchWord(word: String) = apiService.fetchWordDetails(word)


}