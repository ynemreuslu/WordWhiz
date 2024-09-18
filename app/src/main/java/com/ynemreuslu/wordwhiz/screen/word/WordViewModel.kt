package com.ynemreuslu.wordwhiz.screen.word

import androidx.lifecycle.ViewModel
import com.ynemreuslu.wordwhiz.data.WordRepository
import com.ynemreuslu.wordwhiz.data.model.Words
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class WordViewModel @Inject constructor(private val wordRepository: WordRepository) : ViewModel() {
    private val _wordList = MutableStateFlow<List<Words>>(emptyList())
    val wordList: StateFlow<List<Words>> get() = _wordList

    init {
        loadWords()
    }

    fun loadWords() {
        _wordList.value = wordRepository.getWords()

    }

    fun loadWords1(): List<Words> {
        _wordList.value = wordRepository.getLearnedWords()
        return _wordList.value
    }

    fun shuffleWords() {
        _wordList.value = wordRepository.shuffleWords()
    }
}