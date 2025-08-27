package com.camg_apps.memory_butts

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.camg_apps.memory_butts.models.Card
import com.camg_apps.memory_butts.models.CardModel
import com.camg_apps.memory_butts.utils.PreferencesKey
import com.camg_apps.memory_butts.utils.PreferencesProvider

import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.io.File

class MemoryGameViewModel : ViewModel() {

    companion object {
        const val AMOUNT_PAIRS_EASY = 8
        const val AMOUNT_PAIRS_MEDIUM = 12
        const val AMOUNT_PAIRS_DIFFICULT = 15

        private val gameCountDownTime: Long = 61000
        private val timeBetweenTurns: Long = 1500

// "Pirate Butt" to R.raw.pirate,

    }

    var cards  = listOf<CardModel>()



    private var imageViewList: List<ImageView> = listOf()
    private var cardViewList: List<CardView> = listOf()
    private var amountPairs: Int = 0

    private var countDownTimer: CountDownTimer? = null
    private var showPreview: Boolean? = true
    private var autoHide: Boolean? = true

    val iMoves = MutableLiveData<Int>()
    val lTime = MutableLiveData<Long>()
    var gameImageCards = mutableListOf<Card>()
    var isGameOver = false

    var filesDir: File? = null

    fun configure(context: Context) {
        val cardsListJson = PreferencesProvider.string(context, PreferencesKey.CARDS)
        val type = object : TypeToken<List<CardModel>>() {}.type
        cards = Gson().fromJson(cardsListJson, type) as List<CardModel>
        filesDir = context.filesDir
    }

    fun newGame(
        imageViewList: List<ImageView>,
        cardViewList: List<CardView>,
        amountPairs: Int
    //    showPreview: Boolean,
   //     autoHide: Boolean,
    ) {
        this.imageViewList = imageViewList
        this.cardViewList = cardViewList
        this.amountPairs = amountPairs
        this.showPreview = showPreview
        this.autoHide = autoHide
        isGameOver = false
        iMoves.postValue(0)

        val randomCards = getRandomCards()

        for ((i, card) in randomCards.withIndex()) {
            gameImageCards.add(Card(card.english_name, card.fileName, imageViewList[i], cardViewList[i]))
        }

        Log.i("MemoryGameViewModel", "newGame: ${gameImageCards.size}")


        countDownTimer = object : CountDownTimer(gameCountDownTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val segundosRestantes = millisUntilFinished / 1000
                lTime.postValue(segundosRestantes)
            }

            override fun onFinish() {
                isGameOver = true
                lTime.postValue(0)
            }
        }
    }


    fun playGame(context: Context, onWin: () -> Unit) {
        if (showPreview == true) {
            gameImageCards.forEach {
                it.cardView.visibility = View.VISIBLE
                var fileImage = File(filesDir, it.imageName)
                Log.i("MemoryGameViewModel", "playGame: $filesDir, ${it.imageName}")
                Glide.with(context).asGif().load(fileImage).into(it.imageView)
            }
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    iniciarLogica(context, onWin)
                }
            }.start()
        } else {
            iniciarLogica(context, onWin)
        }


    }

    @SuppressLint("SuspiciousIndentation")
    private fun iniciarLogica(context: Context, onWin: () -> Unit) {
        var clicked = 0
        var lastClicked = -1
        var turnOver = false
        var countMoves = 0
        var countPairs = 0


        countDownTimer?.start()
        for (i in 0 until gameImageCards.size) {
            gameImageCards[i].imageView.setImageResource(R.drawable.ic_launcher_foreground)
            gameImageCards[i].cardView.visibility = View.VISIBLE


                gameImageCards[i].imageView.setOnClickListener {

                    if (!gameImageCards[i].isShowing && !turnOver) {
                        var fileImage = File(filesDir, gameImageCards[i].imageName)
                        Log.i("MemoryGameViewModel", "playGame: $filesDir, ${gameImageCards[i].imageName}")
                        Glide.with(context).asGif().load(fileImage)
                            .into(gameImageCards[i].imageView)
                        gameImageCards[i].isShowing = true

                        if (clicked == 0) {
                            lastClicked = i
                        }
                        clicked++
                    } else if (gameImageCards[i].isShowing && !autoHide!!) {
                        gameImageCards[i].imageView.setImageResource(R.drawable.ic_launcher_foreground)
                        gameImageCards[i].isShowing = false
                        clicked--
                    }

                    //Comprobamos si ya selecciono sus dos cartas
                    if (clicked == 2 && turnOver == false) {
                        iMoves.postValue(countMoves++)
                        turnOver = true

                        //Comprobamos si encontró el par
                        if (gameImageCards[i].imageName == gameImageCards[lastClicked].imageName) {
                            clicked = 0


                            //reproducir sonido de pedo con media player
                            val mp = MediaPlayer.create(context, R.raw.pedo)
                            mp.start()


                            countPairs++
                            gameImageCards[i].imageView.isClickable = false
                            gameImageCards[lastClicked].imageView.isClickable = false
                            turnOver = false
                            if (countPairs >= amountPairs && !isGameOver) {
                                countDownTimer?.cancel()
                                onWin()
                            }
                        } else if (autoHide!!) {
                            clicked = 0
                            timeBetweenTurns {
                                gameImageCards[i].imageView.setImageResource(R.drawable.ic_launcher_foreground)
                                gameImageCards[lastClicked].imageView.setImageResource(R.drawable.ic_launcher_foreground)
                                gameImageCards[i].isShowing = false
                                gameImageCards[lastClicked].isShowing = false
                                turnOver = false
                            }
                        }
                    } else if (clicked == 0 && !autoHide!!) {
                        turnOver = false
                    }
                }

        }

    }

    private fun getRandomCards(): MutableList<CardModel> {

        Log.i("MemoryGameViewModel", "getRandomCards: ${cards.size}")
        return cards.asSequence().shuffled().take(amountPairs).toMutableList().apply {
            addAll(this)
            this.shuffle()
        }
    }


    private fun timeBetweenTurns(onFinish: () -> Unit) {
        object : CountDownTimer(timeBetweenTurns, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                onFinish()
            }
        }.start()
    }
}