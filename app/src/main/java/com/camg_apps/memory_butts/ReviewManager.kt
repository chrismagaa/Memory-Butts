package com.camg_apps.memory_butts

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

object ReviewManager{


    private var reviewInfo: ReviewInfo? = null
    private var manager: ReviewManager? = null


    fun activateReviewInfo(context: Context){
        if(manager == null){
            manager = ReviewManagerFactory.create(context)
        }

        val request = manager?.requestReviewFlow()
        request?.addOnCompleteListener {task ->
            if(task.isSuccessful){
                reviewInfo = task.result
            }
        }
    }

    fun startReviewFlow(activity: Activity){
        reviewInfo?.let {
            val flow = manager?.launchReviewFlow(activity, it)
            flow?.addOnCompleteListener { task ->
                Log.d(ReviewManager::class.simpleName, "Complete Review")
            }
        }

    }
}