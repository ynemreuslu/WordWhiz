package com.ynemreuslu.wordwhiz.screen.learned.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ynemreuslu.wordwhiz.databinding.LayoutLearnedItemBinding
import com.ynemreuslu.wordwhiz.data.model.Words

class LearnedWordAdapter(
    private var wordsList: List<Words>,
    private val onItemClickListener: (Words) -> Unit
) : RecyclerView.Adapter<LearnedWordViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LearnedWordViewHolder {
        val binding = LayoutLearnedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LearnedWordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LearnedWordViewHolder, position: Int) {
        holder.bind(wordsList[position], onItemClickListener)
    }

    override fun getItemCount(): Int =
        wordsList.size

    fun updateWords(newWords: List<Words>) {
        wordsList = newWords
        notifyDataSetChanged()
    }
}