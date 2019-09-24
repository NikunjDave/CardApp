package com.cardapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cardapp.model.CardData
import com.cardapp.model.ResponseCard
import com.cardapp.network.ApiService
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CardViewModel(private val cardNavigator: CardNavigator) : ViewModel() {

    private val apiService by lazy { ApiService.create() }
    private lateinit var disposable: Disposable
    fun fetchCardData() {
        cardNavigator.showLoading()
        disposable = apiService.getCards()
            .subscribeOn(Schedulers.io())
            .map { response -> fetchList(response) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<CardData>>() {
                override fun onSuccess(data: List<CardData>) {
                    cardNavigator.onCardFetched(data)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })

    }
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }


    private fun fetchList(json: String): List<CardData> {
        val gson = Gson()
        val response = gson.fromJson<ResponseCard>(json.substring(1),ResponseCard::class.java)
       /* val typeToken = object : TypeToken<ArrayList<CardData>>() {}
        val list = gson.fromJson<ArrayList<CardData>>(json.substring(1), typeToken.type)*/
        return response.cardList!!
    }
}

