package com.ynemreuslu.wordwhiz.screen.word.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ynemreuslu.wordwhiz.databinding.LayoutWordItemBinding
import com.ynemreuslu.wordwhiz.data.model.Words

class WordAdapter(
    private var words: List<Words>, private val onClick: (Words) -> Unit
) : RecyclerView.Adapter<WordViewHolder>() {

    fun updateWords(newWords: List<Words>) {
        val diffResult = DiffUtil.calculateDiff(WordDiffCallback(words, newWords))
        words = newWords
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = LayoutWordItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.bind(word, onClick)

    }

    override fun getItemCount() = words.size

}

