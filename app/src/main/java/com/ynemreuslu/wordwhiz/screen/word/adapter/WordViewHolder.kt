package com.ynemreuslu.wordwhiz.screen.word.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ynemreuslu.wordwhiz.databinding.LayoutWordItemBinding
import com.ynemreuslu.wordwhiz.data.model.Words

class WordViewHolder(
    val binding: LayoutWordItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    inline fun bind(
        word: Words, crossinline onItemClickListener: (Words) -> Unit
    ) {
        binding.tvEnglish.text = word.english
        binding.tvTurkish.text = word.turkish

        binding.btnClickDetails.setOnClickListener {
            onItemClickListener(word)
        }
    }
}