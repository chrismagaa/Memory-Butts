package com.camg_apps.memory_butts

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object FirebaseRCService {

    private const val CLAVE_LAST_AVAILABLE_VERSION = "last_available_version"

    private val last_available_version_value = "1.0"

    val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }


    fun configureRemoteConfig() {
        val firebaseConfig = Firebase.remoteConfig
        firebaseConfig.setConfigSettingsAsync(configSettings)

        firebaseConfig.setDefaultsAsync(
            mapOf(CLAVE_LAST_AVAILABLE_VERSION to last_available_version_value))
    }

    fun getAvailableVersion(showDialogLastVersion: (String) -> Unit){
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
            if(it.isSuccessful){
                val last_version = Firebase.remoteConfig.getString(CLAVE_LAST_AVAILABLE_VERSION)
                showDialogLastVersion(last_version)
            }

        }
    }

}