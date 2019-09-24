package com.cardapp.ui

import com.cardapp.model.CardData

interface CardNavigator {
    fun onCardFetched(cards : List<CardData>)
    fun showLoading()
}