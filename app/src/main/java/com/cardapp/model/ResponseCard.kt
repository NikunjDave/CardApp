package com.cardapp.model

import com.google.gson.annotations.SerializedName

class ResponseCard {
    @SerializedName("data")
    val cardList : List<CardData>? = null
}