package com.camg_apps.memory_butts.models

import android.widget.ImageView
import androidx.cardview.widget.CardView

data class Card(
    val name: String,
    val imageName: String,
    val imageView: ImageView,
    val cardView: CardView,
    var isShowing: Boolean = false,
)
