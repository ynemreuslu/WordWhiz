package com.ynemreuslu.wordwhiz.screen.word.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ynemreuslu.wordwhiz.data.model.Words

class WordDiffCallback(
    private val oldList: List<Words>, private val newList: List<Words>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}