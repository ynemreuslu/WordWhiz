package com.ynemreuslu.wordwhiz.screen.learned.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ynemreuslu.wordwhiz.databinding.LayoutLearnedItemBinding
import com.ynemreuslu.wordwhiz.data.model.Words

class LearnedWordViewHolder(val binding: LayoutLearnedItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    inline fun bind(
        word: Words,
        crossinline onItemClickListener: (Words) -> Unit
    ) {
        binding.tvEnglish.text = word.english
        binding.tvTurkish.text = word.turkish

        binding.btnClickDetails.setOnClickListener {
            onItemClickListener(word)
        }
    }
}