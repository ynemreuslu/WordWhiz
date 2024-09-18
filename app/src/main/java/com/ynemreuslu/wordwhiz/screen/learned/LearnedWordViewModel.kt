package com.ynemreuslu.wordwhiz.screen.learned

import androidx.lifecycle.ViewModel
import com.ynemreuslu.wordwhiz.data.WordRepository
import com.ynemreuslu.wordwhiz.data.model.Words
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LearnedWordViewModel @Inject constructor(private val wordRepository: WordRepository) :
    ViewModel() {

    private val _learnedWords = MutableStateFlow<List<Words>>(emptyList())
    val learnedWords: StateFlow<List<Words>> get() = _learnedWords

    private val _wordList = MutableStateFlow<List<Words>>(emptyList())
    val wordList: StateFlow<List<Words>> get() = _learnedWords


    fun getLearnedWords() {
        _learnedWords.value = wordRepository.getLearnedWords()
    }

    fun deleteWord(word: Words) {
        wordRepository.removeWordFromLearnedList(word)
        _learnedWords.value = wordRepository.getLearnedWords()
        wordRepository.addWord(word)
        _wordList.value = wordRepository.getWords()
    }

    fun isLearningListEmpty(): Boolean {
        return wordRepository.isLearningListEmpty()
    }

}