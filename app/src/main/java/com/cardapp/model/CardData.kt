package com.cardapp.model

data class CardData (
    var id : Int = 0,
    var text : String ? = null,
    var uid : Long = counter++
    ) {
    companion object {
        private var counter = 0L
    }
}