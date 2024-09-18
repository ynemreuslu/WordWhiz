package com.ynemreuslu.wordwhiz.screen.worddetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.wordwhiz.data.WordRepository
import com.ynemreuslu.wordwhiz.data.model.Words
import com.ynemreuslu.wordwhiz.data.model.remote.WordData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailsViewModel @Inject constructor(
    private val wordRepository: WordRepository,
) : ViewModel() {

    private var _wordList = MutableStateFlow<List<Words>>(emptyList())
    val wordList: StateFlow<List<Words>> get() = _wordList


    private val _learnedWordList = MutableStateFlow<List<Words>>(emptyList())
    val learnedWordList: StateFlow<List<Words>> get() = _learnedWordList

    private val _wordDetails = MutableLiveData<List<WordData>>()
    val wordDetails: LiveData<List<WordData>> get() = _wordDetails

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    fun addWordToLearnedList(word: Words) {
        wordRepository.addWordToLearnedList(word)
        _learnedWordList.value = wordRepository.getLearnedWords()
        removeWorkSharedList(word)
    }

    fun isWordInLearnedList(word: Words): Boolean {
        return wordRepository.isWordInLearnedList(word)
    }


    private fun removeWorkSharedList(word: Words) {
        wordRepository.removeWord(word)
        _wordList.value = wordRepository.getWords()
    }

    fun addWord(word: Words) {
        wordRepository.addWord(word)
        _wordList.value = wordRepository.getWords()
    }

    fun fetchWordDetails(word: String) {
        viewModelScope.launch {
            val result = wordRepository.fetchWord(word)
            result.let {
                _wordDetails.postValue(it.body())
            }

        }
    }
}
