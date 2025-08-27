package com.camg_apps.memory_butts.utils

import android.content.Context
import android.content.SharedPreferences


enum class PreferencesKey(val value: String){
    RANKING_EASY("EASY"),
    RANKING_MEDIUM("MEDIUM"),
    RANKING_DIFFICULT("DIFFICULT"),
    CARDS("CARDS"),
}

object PreferencesProvider {

    fun set(context: Context, key: PreferencesKey, value: String? = null){
        val editor = prefs(context).edit()
        editor.putString(key.value, value).apply()
    }

    fun remove (context: Context, key: PreferencesKey){
        val editor = prefs(context).edit()
        editor.remove(key.value).apply()
    }

    fun string(context: Context, key: PreferencesKey): String?{
        return prefs(context).getString(key.value, null)
    }

    fun clear(context: Context){
        val editor = prefs(context).edit()
        editor.clear().apply()
    }

    private fun prefs(context: Context): SharedPreferences {
        return  context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}