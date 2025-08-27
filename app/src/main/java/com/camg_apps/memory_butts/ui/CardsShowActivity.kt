package com.camg_apps.memory_butts.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.camg_apps.memory_butts.models.CardModel
import com.camg_apps.memory_butts.theme.MemoryButtsTheme
import com.camg_apps.memory_butts.utils.PreferencesKey
import com.camg_apps.memory_butts.utils.PreferencesProvider
import com.google.gson.Gson
import java.io.File
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.camg_apps.memory_butts.R
import com.camg_apps.memory_butts.theme.BlackButt
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.GlideRequestType

class CardsShowActivity : ComponentActivity() {

    var myFilesDir: File? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var cardsJson = PreferencesProvider.string(this, PreferencesKey.CARDS)
        var cards = Gson().fromJson(cardsJson, Array<CardModel>::class.java) ?: emptyArray()

        Log.i("CardsShowActivity", "cards: ${cards.size}")

        myFilesDir = this.filesDir
        setContent {
            MemoryButtsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFf4f4f6),
                topBar = {
                    //top bar
                    TopAppBar(
                        colors = TopAppBarColors(
                            containerColor = Color(0xFFf4f4f6),
                            titleContentColor = Color.Black,
                            scrolledContainerColor = Color(0xFFf4f4f6),
                            actionIconContentColor = Color(0xFFf4f4f6),
                            navigationIconContentColor = Color(0xFFf4f4f6)
                        ),
                        navigationIcon = {
                            Icon(
                                Icons.Default.ArrowBack, contentDescription = "Back",
                                //color black
                                tint = BlackButt,
                                modifier = Modifier.
                                padding(top = 10.dp).
                                size(50.dp).
                                clickable {
                                    goToMainActivity(this@CardsShowActivity)
                                }
                            )
                        },
                        title = {
                            TitleRanking("Cards")
                        })
                },
               content =  { innerPadding ->
                    //center
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        CardsList(
                            cards = cards.toList(),
                            modifier = Modifier.padding(innerPadding),
                            context = this@CardsShowActivity
                        )
                    }


                })
            }
        }
    }


    @Composable
    fun CardsList(cards: List<CardModel>, modifier: Modifier = Modifier, context: Context) {


        LazyRow (
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            content = {
                items(cards.size) { i ->
                    CardItem(card = cards[i], context = context)
                }
            }
        )


    }

    @Composable
    fun CardItem(card: CardModel, context: Context) {
        var fileImage = File(myFilesDir, card.fileName)
        Log.i("CardItem", "fileImage: ${fileImage}")



            Card(

                shape = RoundedCornerShape(40.dp),
                border = BorderStroke(2.dp, BlackButt),

               ) {
                Box(
                    //add stroke

                    modifier = Modifier.background(Color.White)

                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .background(Color.White),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        GlideImage(
                            glideRequestType = GlideRequestType.GIF,
                            imageModel = { fileImage },
                            imageOptions = ImageOptions(
                                    contentScale = androidx.compose.ui.layout.ContentScale.FillWidth,
                            ),
                            modifier = Modifier
                                .width(300.dp)
                                .height(400.dp)
                        )
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .background(Color.White),
                            color = BlackButt,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.teko_bold)),
                            fontSize = 40.sp,
                            text = card.english_name)
                    }
                }





        }









    }




}







