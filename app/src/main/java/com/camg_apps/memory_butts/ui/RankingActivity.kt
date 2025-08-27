package com.camg_apps.memory_butts.ui

import android.app.Activity
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.TypedArrayUtils.getString
import com.camg_apps.memory_butts.R
import com.camg_apps.memory_butts.theme.BlackButt
import com.camg_apps.memory_butts.theme.MemoryButtsTheme
import com.camg_apps.memory_butts.utils.RankingControl
import com.camg_apps.memory_butts.utils.RankingModel



class RankingActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("RankingActivity", "onCreate")
        Log.i("RankingActivity", "rankingEasyOrdered: ${RankingControl.rankingEasyOrdered}")
        Log.i("RankingActivity", "rankingMediumOrdered: ${RankingControl.rankingMediumOrdered}")
        Log.i("RankingActivity", "rankingDifficultOrdered: ${RankingControl.rankingDifficultOrdered}")

        //change color status bar




        setContent {
            MemoryButtsTheme {
                // A surface container using the 'background' color from the theme

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFE0E0E0),
                    topBar = {
                        //set top bar
                       TopAppBar(
                           colors = TopAppBarColors(
                               containerColor = Color.White,
                               titleContentColor = Color.Black,
                               scrolledContainerColor = Color.White,
                               actionIconContentColor = Color.White,
                               navigationIconContentColor = Color.White
                           ),
                            navigationIcon = {
                                 Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                                        //color black
                                        tint = BlackButt,
                                      modifier = Modifier.
                                            padding(top = 10.dp).
                                          size(50.dp).
                                      clickable {
                                        goToMainActivity(this@RankingActivity)
                                      }
                                 )
                            },
                           title = {
                                 TitleRanking("Ranking")
                           })
                    },

                    content = { innerPadding ->
                      Surface(
                          modifier = Modifier.fillMaxSize().padding(innerPadding),
                          color = MaterialTheme.colorScheme.background
                      ) {
                          RankingView(this@RankingActivity)
                      }
                  })


            }
        }
    }
}

@Composable
fun RankingView(activity: Activity) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        val checkedEasy = remember { mutableStateOf(true) }
        val checkedMedium = remember { mutableStateOf(false) }
        val checkedDifficult = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            Spacer(modifier = Modifier.size(16.dp))

            //set checkboxs for select the level
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row {
                    Checkbox(
                        checked = checkedEasy.value,
                        onCheckedChange = {
                            checkedEasy.value = it
                            checkedMedium.value = false
                            checkedDifficult.value = false
                        },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = Color.White,
                            checkedColor = BlackButt,
                            uncheckedColor = BlackButt
                        ),
                        //change style of checkbox
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Text(text = activity.getString(R.string.facil),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.teko_bold)),
                        style = MaterialTheme.typography.bodyLarge,
                        color = BlackButt,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Row {
                    Checkbox(
                        checked = checkedMedium.value,
                        onCheckedChange = {
                            checkedEasy.value = false
                            checkedMedium.value = it
                            checkedDifficult.value = false
                        },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = Color.White,
                            checkedColor = BlackButt,
                            uncheckedColor = BlackButt
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text(text = activity.getString(R.string.medio),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily(Font(R.font.teko_bold)),
                        color = BlackButt,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Row {
                    Checkbox(
                        checked = checkedDifficult.value,
                        onCheckedChange = {
                            checkedEasy.value = false
                            checkedMedium.value = false
                            checkedDifficult.value = it
                        },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = Color.White,
                            checkedColor = BlackButt,
                            uncheckedColor = BlackButt
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text(text = activity.getString(R.string.dificil),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily(Font(R.font.teko_bold)),
                        color = BlackButt,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }


            when {
                checkedEasy.value -> ListRankingEasy()
                checkedMedium.value -> ListRankingMedium()
                checkedDifficult.value -> ListRankingDifficult()
            }

        }



    }
}

fun goToMainActivity(activity: Activity) {
    Log.i("RankingActivity", "goToMainActivity")
    activity.finish()
   // val intent = Intent(activity, MainActivity::class.java)
   // startActivity(activity, intent, null)
  //  activity.finish()
    //
}

@Composable
fun ListRankingDifficult() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(RankingControl.rankingDifficultOrdered.size) { index ->
            val ranking = RankingControl.rankingDifficultOrdered[index]
            RankingItemCard(ranking, index+1)
        }
    }
}

@Composable
fun ListRankingMedium() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(RankingControl.rankingMediumOrdered.size) { index ->
            val ranking = RankingControl.rankingMediumOrdered[index]
            RankingItemCard(ranking, index+1)
        }
    }
}

@Composable
fun ListRankingEasy() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(RankingControl.rankingEasyOrdered.size) { index ->
            val ranking = RankingControl.rankingEasyOrdered[index]
            RankingItemCard(ranking, index+1)
        }
    }
}

@Composable
fun RankingItemCard(item: RankingModel, position: Int){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp, vertical = 8.dp)
            .background(Color.White)
    ) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        border = BorderStroke(2.dp, Color.Black),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(BlackButt),

        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ){
                Text(text = position.toString(),
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily(Font(R.font.teko_bold)),
                    color = Color.Black,
                )
            }
            Text(text = item.name,
                fontSize = 26.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.teko_light)),
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(text = "${item.score.toString()} pts",
                fontSize = 24.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.teko_bold)),
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )



        }
    }
    }
}





@Composable
fun TitleRanking(title: String) {
    Text(text = title,
        Modifier.padding(top = 16.dp),
        fontSize = 50.sp,
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = FontFamily(Font(R.font.teko_bold)),
        color = BlackButt,
    )
}

