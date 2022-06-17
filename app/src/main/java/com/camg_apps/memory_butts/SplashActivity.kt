package com.camg_apps.memory_butts

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.camg_apps.memory_butts.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRemoteConfig()
    }


    private fun setupRemoteConfig() {
        FirebaseRCService.configureRemoteConfig()
        FirebaseRCService.getAvailableVersion { lastVersion ->
            val currentVersion = BuildConfig.VERSION_NAME
            if(lastVersion.toFloat() > currentVersion.toFloat()){
                showDialogUpdate()
            }else{
                goToMainActivity()
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showDialogUpdate(){
        val dialog =  UpdateAppDialogFragment{goToPlayStore()}
        if(!dialog.isAdded){
            dialog.show(supportFragmentManager, "SHOW DIALOG")
        }
    }

    private fun goToPlayStore() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID)
            setPackage("com.android.vending")
        }
            startActivity(intent)
    }
}