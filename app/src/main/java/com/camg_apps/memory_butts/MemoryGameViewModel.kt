package com.camg_apps.memory_butts

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide

class MemoryGameViewModel : ViewModel() {

    companion object {
        const val AMOUNT_PAIRS_EASY = 8
        const val AMOUNT_PAIRS_MEDIUM = 12
        const val AMOUNT_PAIRS_DIFFICULT = 15

        private val gameCountDownTime: Long = 61000
        private val timeBetweenTurns: Long = 1500

        private val allResourceImages = listOf(
            "Alien Butt" to R.raw.alien,
            "Bongo Butt" to R.raw.bongo,
            "Censored Butt" to R.raw.censored,
            "Business Butt" to R.raw.business,
            "Diver Butt" to R.raw.diver,
            "Cow Butt" to R.raw.cow,
            "Easter Butt" to R.raw.easter,
            "Genius Butt" to R.raw.eistein,
            "Flaming Butt" to R.raw.flaming,
            "Frankenstein Butt" to R.raw.frankenstein,
            "Glasses Butt" to R.raw.glasses,
            "Magic Butt" to R.raw.magic,
            "Buttless Butt" to R.raw.no,
            "Pirate Butt" to R.raw.pirate,
            "Space Butt" to R.raw.space,
            "Jeans Butt" to R.raw.jeans,
            "Frida Butthlo" to R.raw.frida,
            "Superhero Butt" to R.raw.superhero,
            "Triple Butt" to R.raw.triple,
            "Thunder Butt" to R.raw.thunder,
            "Zebra Butt" to R.raw.zebra,
            "Clapping Butt" to R.raw.clapping,
            "Flexing Butt" to R.raw.flexing,
            "Hairy Butt" to R.raw.hair,
            "Samba Butt" to R.raw.samba,
            "Seeing Butt" to R.raw.seeing,
            "Shivering Butt" to R.raw.shivering,
            "Little Butt" to R.raw.small,
            "Underwater Butt" to R.raw.underwater,
            "UX Designer Butt" to R.raw.ux
        )
    }


    private var imageViewList: List<ImageView> = listOf()
    private var cardViewList: List<CardView> = listOf()
    private var amountPairs: Int = 0

    private var countDownTimer: CountDownTimer? = null
    private var showPreview: Boolean? = null
    private var autoHide: Boolean? = null

    val iMoves = MutableLiveData<Int>()
    val lTime = MutableLiveData<Long>()
    var gameImageCards = mutableListOf<Card>()
    var isGameOver = false


    fun newGame(
        imageViewList: List<ImageView>,
        cardViewList: List<CardView>,
        amountPairs: Int,
        showPreview: Boolean,
        autoHide: Boolean,
    ) {
        this.imageViewList = imageViewList
        this.cardViewList = cardViewList
        this.amountPairs = amountPairs
        this.showPreview = showPreview
        this.autoHide = autoHide
        isGameOver = false
        iMoves.postValue(0)

        val randomResourceImages = getRandomResourceImages()

        for ((i, image) in randomResourceImages.withIndex()) {
            gameImageCards.add(
                Card(image.first, image.second, imageViewList[i], cardViewList[i])
            )
        }


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
                Glide.with(context).asGif().load(it.imageResource).into(it.imageView)
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
                        Glide.with(context).asGif().load(gameImageCards[i].imageResource)
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
                        if (gameImageCards[i].imageResource == gameImageCards[lastClicked].imageResource) {
                            clicked = 0
                            Toast.makeText(context, gameImageCards[i].name, Toast.LENGTH_SHORT)
                                .show()

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

    private fun getRandomResourceImages(): MutableList<Pair<String, Int>> {
        return allResourceImages.asSequence().shuffled().take(amountPairs).toMutableList().apply {
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