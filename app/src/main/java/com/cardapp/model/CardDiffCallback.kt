package com.cardapp.model

import androidx.recyclerview.widget.DiffUtil

class CardDiffCallback(private val old: List<CardData>, private val new : List<CardData>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].uid == new[newItemPosition].uid
    }

    override fun getOldListSize(): Int {
      return  old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }

}
