package com.camg_apps.memory_butts.utils

import android.content.Context
import com.camg_apps.memory_butts.utils.RankingControl.configure
import com.google.gson.Gson


import com.google.gson.reflect.TypeToken

object RankingControl {

    var rankingEasyOrdered: List<RankingModel> = emptyList()
    var rankingMediumOrdered: List<RankingModel> = emptyList()
    var rankingDifficultOrdered: List<RankingModel> = emptyList()

    fun configure(context: Context) {
        rankingEasyOrdered = getRankingOrdered(context, PreferencesKey.RANKING_EASY)
        rankingMediumOrdered = getRankingOrdered(context, PreferencesKey.RANKING_MEDIUM)
        rankingDifficultOrdered = getRankingOrdered(context, PreferencesKey.RANKING_DIFFICULT)
    }

    fun getRankingOrdered(context: Context, rankingPrefrerences: PreferencesKey): List<RankingModel> {
       return  getRanking(context, rankingPrefrerences).sortedByDescending { it.score }

    }

    private fun getRanking(context: Context, rankingPrefrerences: PreferencesKey): List<RankingModel> {

        val rankingString = PreferencesProvider.string(context, rankingPrefrerences)

        return if (rankingString.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<RankingModel>>() {}.type
            Gson().fromJson(rankingString, type)
        }
    }



    fun saveRanking(context: Context, ranking: List<RankingModel>, rankingPrefrerences: PreferencesKey) {
        val rankingString = Gson().toJson(ranking)
        PreferencesProvider.set(context, rankingPrefrerences, rankingString)
        configure(context)
    }

    fun addRanking(context: Context, ranking: RankingModel) {
        var rankingPrefrerences = when (ranking.amountPairs) {
            8 -> PreferencesKey.RANKING_EASY
            12 -> PreferencesKey.RANKING_MEDIUM
            else -> PreferencesKey.RANKING_DIFFICULT
        }

        val rankingList = getRanking(context, rankingPrefrerences).toMutableList()
        rankingList.add(ranking)
        saveRanking(context, rankingList, rankingPrefrerences)
    }

    fun clearRanking(context: Context) {
        PreferencesProvider.remove(context, PreferencesKey.RANKING_EASY)
        PreferencesProvider.remove(context, PreferencesKey.RANKING_MEDIUM)
        PreferencesProvider.remove(context, PreferencesKey.RANKING_DIFFICULT)

    }

    fun getRankingSize(context: Context, rankingPrefrerences: PreferencesKey): Int {
        return getRanking(context, rankingPrefrerences).size
    }

    fun getRankingPosition(context: Context, ranking: RankingModel, rankingPrefrerences: PreferencesKey): Int {
        return getRanking(context, rankingPrefrerences).indexOf(ranking)
    }

    fun getRankingPosition(context: Context, score: Int, rankingPrefrerences: PreferencesKey): Int {
        val ranking = getRanking(context, rankingPrefrerences)
        return ranking.indexOfFirst { it.score == score }
    }

    fun getRankingPosition(context: Context, name: String, rankingPrefrerences: PreferencesKey): Int {
        val ranking = getRanking(context, rankingPrefrerences)
        return ranking.indexOfFirst { it.name == name }
    }




}