package com.camg_apps.memory_butts

import android.content.Context
import android.util.Log
import com.camg_apps.memory_butts.models.CardModel
import com.camg_apps.memory_butts.utils.PreferencesKey
import com.camg_apps.memory_butts.utils.PreferencesProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import java.io.File

class RealtimeManager {

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("cards")


    var storageCards = Firebase.storage.reference.child("cards")

    fun getCards(context: Context, progress: (Int) -> Unit, onFinish: () -> Unit, onFail: () -> Unit) {
        databaseReference.get().addOnSuccessListener {
            val cards = it.value as HashMap<String, Any>
            Log.i("RealtimeManager", "cards: $cards")
            var cardsList = listOf<CardModel>()

            cards.forEach { (key, value) ->
                val card = value as HashMap<String, Any>
                val englishName = card["english_name"] as String
                val spanishName = card["spanish_name"] as String
                val fileName = card["fileName"] as String

                cardsList += CardModel(englishName, spanishName, fileName)
            }

            //guardar en preferences
            var cardsListJson = Gson().toJson(cardsList)
            PreferencesProvider.set(context, PreferencesKey.CARDS, cardsListJson)

            downloadFileGifs(context,progress, cardsList, onFinish, onFail)

            Log.i("RealtimeManager", "cardsList: $cardsList")
        }.addOnFailureListener {
            onFail()
            Log.e("RealtimeManager", "Error getting data", it)
        }

        }

    private fun downloadFileGifs(context: Context, progress: (Int) -> Unit, cardsList: List<CardModel>, onFinish: () -> Unit, onFail: () -> Unit) {
        var progressCount = 0
        cardsList.forEach {

            val imageRef = storageCards.child(it.fileName)

            //almacenar fle en Internal Storage
            val localFile = File(context.filesDir, it.fileName)

            imageRef.getFile(localFile).addOnSuccessListener {
                Log.i("RealtimeManager", "File downloaded")

            }.addOnFailureListener {
                onFail()
                Log.e("RealtimeManager", "Error downloading file", it)
            }

            progressCount += 100 / cardsList.size
            progress(progressCount)

        }

        onFinish()
    }

}
