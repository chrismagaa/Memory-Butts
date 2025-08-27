package com.camg_apps.memory_butts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.notification.NotificationListenerService.Ranking
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.camg_apps.memory_butts.databinding.ActivityMainBinding
import com.camg_apps.memory_butts.ui.fragments.MemoryGameFragment
import com.camg_apps.memory_butts.utils.RankingControl

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get extras
        val extras = intent.extras
        if(extras != null){
            //VOLVER A JUGAR
            val amounPairs = extras.getInt("amountPairs")
            val bundle = bundleOf(MemoryGameFragment.ARG_AMOUNT_PAIRS to amounPairs)

            findNavController(R.id.navigation).navigate(R.id.memoryGameFragment, bundle)

        }



        RankingControl.configure(this)
    }

}