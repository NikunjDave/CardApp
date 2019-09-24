package com.cardapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cardapp.R
import com.cardapp.model.CardData

class CardStackAdapter( private var cards: List<CardData> = emptyList()) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_card, parent, false))
    }

    override fun getItemCount(): Int {
       return cards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        holder.num.text = ""+card.id
        holder.description.text = card.text
    }
    fun setCards(spots: List<CardData>) {
        this.cards = spots
    }
    fun addCard(card: CardData) {
        this.cards.plus(card)
    }

    fun getCards(): List<CardData> {
        return cards
    }

    fun getPreviousCards(position: Int): CardData {
        return cards.get(position)
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val num: TextView = view.findViewById(R.id.item_no)
        var description: TextView = view.findViewById(R.id.item_desc)
    }
}