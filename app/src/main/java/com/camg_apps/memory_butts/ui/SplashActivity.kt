package com.camg_apps.memory_butts.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.camg_apps.memory_butts.MainActivity
import com.camg_apps.memory_butts.RealtimeManager
import com.camg_apps.memory_butts.databinding.ActivitySplashBinding
import com.camg_apps.memory_butts.utils.PreferencesKey
import com.camg_apps.memory_butts.utils.PreferencesProvider

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    var rm = RealtimeManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardsList = PreferencesProvider.string(this, PreferencesKey.CARDS)

        if (cardsList != null) {
            goToMainActivity()
            return
        }

        binding.pbImages.progress = 0
        binding.pbImages.max = 100
        binding.pbImages.progressDrawable.setColorFilter(
            resources.getColor(android.R.color.black),
            android.graphics.PorterDuff.Mode.SRC_IN
        )



        rm.getCards(this,
            { progress ->
                //progress
                binding.pbImages.progress = progress
            },
            {
                goToMainActivity()
        }, {
            Toast.makeText(this, "Algo fallo", Toast.LENGTH_SHORT).show()
        })




    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}