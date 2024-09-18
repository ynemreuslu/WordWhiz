package com.ynemreuslu.wordwhiz.screen.activity

import androidx.lifecycle.ViewModel
import com.ynemreuslu.wordwhiz.data.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {

    fun saveJsonDataToSharedPreferences() {
        wordRepository.saveJsonDataToSharedPrefsIfFirstTime()
    }
}