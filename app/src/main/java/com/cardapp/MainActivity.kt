package com.cardapp

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.cardapp.model.CardData
import com.cardapp.model.CardDiffCallback
import com.cardapp.ui.CardStackAdapter
import com.cardapp.ui.CardNavigator
import com.cardapp.ui.CardViewModel
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), CardNavigator, CardStackListener {


    private val cardViewMode by lazy { CardViewModel(this) }
    private val manager by lazy { CardStackLayoutManager(this, this) }

    private lateinit var adapter: CardStackAdapter

    private var cardList = arrayListOf<CardData>()


    private var progressOffset: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        setListeners()
        cardViewMode.fetchCardData()

    }


    private fun updateProgressBar(){
        progressBar.progress = progressOffset * (manager.topPosition +1)
    }


    private fun setListeners() {
        ivNext.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()

            reloadButton.setOnClickListener {
                reload()
            }

        }

        ivPrevious.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            cardStackView.rewind()
            updateProgressBar()
        }

    }

    private fun resetProgress(){
        progressOffset = 10
        progressBar.progress = progressOffset
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
    }

    private fun reload() {
        val old = adapter.getCards()
        val new = createCardData(old)
        val callback = CardDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setCards(new)
        result.dispatchUpdatesTo(adapter)
        resetProgress()
        emptyCard.visibility = View.GONE
    }


    override fun showLoading() {
        loading.visibility = View.VISIBLE
        rlRoot.visibility = View.GONE
    }


    override fun onCardFetched(cards: List<CardData>) {

        loading.visibility = View.GONE
        rlRoot.visibility = View.VISIBLE

        progressBar.max = cards.size * progressOffset

        progressBar.progress = progressOffset


        cardList = cards as ArrayList<CardData>
        adapter = CardStackAdapter(cardList)
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
        updateProgressBar()

    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
        if(emptyCard.visibility == View.VISIBLE) {
            emptyCard.visibility = View.GONE
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
      if(manager.topPosition == cardList.size -1)
            emptyCard.visibility = View.VISIBLE
    }

    /** creating a new data set
     */
    private fun createCardData(oldData: List<CardData>): List<CardData> {
        val newdData = ArrayList<CardData>()
        for (data in oldData) {
            val item = CardData(data.id, data.text)
            newdData.add(item)
        }

        return newdData
    }

}
