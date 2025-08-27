package com.camg_apps.memory_butts.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.camg_apps.memory_butts.MainActivity
import com.camg_apps.memory_butts.R
import com.camg_apps.memory_butts.theme.BlackButt
import com.camg_apps.memory_butts.theme.MemoryButtsTheme
import com.camg_apps.memory_butts.utils.RankingControl
import com.camg_apps.memory_butts.utils.RankingModel
import java.util.Date

class EndGameActivity : ComponentActivity() {

    var seconds = 0;
    var amountPairs = 0;
    var movimientos = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //get extra
        val bundle = intent.extras
        seconds = bundle!!.getInt("seconds", 0)
         amountPairs = bundle.getInt("amountPairs")
         movimientos = bundle.getInt("movimientos")

        var puntaje = (amountPairs * 1000) - (movimientos * 10) - (seconds * 10)


        setContent {
            MemoryButtsTheme {
                    CardEndGame(activity = this, seconds, amountPairs, movimientos, puntaje)
            }
        }
    }


    fun saveRecord(nombre: String, score: Int, tiempo: Int, amountPairs: Int, movimientos: Int,  activity: Activity) {
        if(nombre.isEmpty()) {
            Toast.makeText(activity, getString(R.string.ingresa_un_nombre), Toast.LENGTH_SHORT).show()
            return
        }
        val rankingData = RankingModel(
            name = nombre,
            score = score,
            time = tiempo,
            movimientos = movimientos,
            amountPairs = amountPairs,
            date = Date().time.toString()
        )

        RankingControl.addRanking(activity,rankingData)
        goToRankingActivity(activity)
    }

    fun goToRankingActivity(context: Context) {
        val intent = Intent(context, RankingActivity::class.java)
        context.startActivity(intent)
        //terminar actividad
        (context as EndGameActivity).finish()
    }




//preview
@Composable
fun CardEndGame(activity: Activity, seconds: Int, amountPairs: Int, movimientos: Int, puntaje: Int) {
    val nombreRecord = remember{ mutableStateOf("") }


     var dificultad = when(amountPairs){
        8 ->  getString(R.string.facil)
        12 ->  getString(R.string.medio)
        15 ->  getString(R.string.dificil)
         else -> {
                getString(R.string.facil)
         }
     }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = Color.White,

        contentColor = Color.Black,

        shadowElevation = 16.dp,
        border = BorderStroke(1.dp, Color.Black),
        tonalElevation = 16.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //set image logo
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = "PEDORY",
                    color = BlackButt,
                    fontFamily = FontFamily(Font(R.font.teko_bold)),

                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp,
                )
            }

            Spacer(modifier = Modifier.size(14.dp))


            if (seconds == 0) {
                Text(
                    text = getString(R.string.perdiste),
                    color = BlackButt,
                    fontFamily = FontFamily(Font(R.font.teko_bold)),
                    fontSize = 50.sp,
                )
            }

                if (seconds != 0) {


                    Column(
                        //set items in start
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()


                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${getString(R.string.dificultad)}:",
                                fontFamily = FontFamily(Font(R.font.teko_light)),
                                color = BlackButt,
                                fontSize = 30.sp,
                            )
                            Text(
                                text = "$dificultad",
                                fontFamily = FontFamily(Font(R.font.teko_bold)),
                                color = BlackButt,
                                fontSize = 30.sp,
                            )
                        }

                        Spacer(modifier = Modifier.size(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${getString(R.string.tiempo)}:",
                                color = BlackButt,
                                fontFamily = FontFamily(Font(R.font.teko_light)),
                                fontSize = 30.sp
                            )
                            Text(
                                text = "$seconds s",
                                color = BlackButt,
                                fontFamily = FontFamily(Font(R.font.teko_bold)),
                                fontSize = 30.sp
                            )
                        }


                        Spacer(modifier = Modifier.size(4.dp))


                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${getString(R.string.movimientos)}:",
                                fontFamily = FontFamily(Font(R.font.teko_light)),
                                color = BlackButt,
                                fontSize = 30.sp,
                            )
                            Text(
                                text = "$movimientos",
                                fontFamily = FontFamily(Font(R.font.teko_bold)),
                                color = BlackButt,
                                fontSize = 30.sp,
                            )
                        }

                        Spacer(modifier = Modifier.size(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${getString(R.string.puntaje)}:",
                                fontFamily = FontFamily(Font(R.font.teko_light)),
                                color = BlackButt,
                                fontSize = 30.sp,
                            )
                            Text(
                                text = "$puntaje",
                                fontFamily = FontFamily(Font(R.font.teko_bold)),
                                color = BlackButt,
                                fontSize = 30.sp,
                            )
                        }

                    }
                }
                if (seconds != 0) {
                Spacer(modifier = Modifier.size(16.dp))

                TextField(
                    value = nombreRecord.value,
                    textStyle = TextStyle(
                        color = BlackButt,
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.teko_bold))
                    ),
                    onValueChange = {
                        nombreRecord.value = it
                    },
                    placeholder = {
                        Text(
                            getString(R.string.nombre),
                            color = Color.Black,
                            fontStyle = FontStyle.Italic,
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(R.font.teko_light))
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Column {

                    if (seconds != 0) {

                        Button(
                            onClick = {
                                saveRecord(
                                    nombreRecord.value,
                                    puntaje,
                                    seconds,
                                    amountPairs,
                                    movimientos,
                                    activity
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BlackButt,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()


                        ) {
                            Text(
                                text = getString(R.string.guardar_record),
                                modifier = Modifier.align(Alignment.CenterVertically),
                                color = Color.White,

                                fontSize = 24.sp
                            )
                        }

                    }



                    Spacer(modifier = Modifier.size(16.dp))
                    Button(
                        onClick = {

                            // go toMain
                            //     val bundle = Bundle()
                            //     bundle.putInt("amountPairs", amountPairs)

                            val intent = Intent(activity, MainActivity::class.java)
                            // intent.putExtras(bundle)
                            activity.startActivity(intent)
                            activity.finish()

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BlackButt,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = getString(R.string.menu_principal),
                            modifier = Modifier.align(Alignment.CenterVertically),
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                }

            }

        }
    }





}



