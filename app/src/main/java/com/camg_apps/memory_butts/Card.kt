package com.camg_apps.memory_butts

import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView

data class Card(
    val name: String,
    val imageResource: Int,
    val imageView: ImageView,
    val cardView: CardView,
    var isShowing: Boolean = false,
)
