package com.pivincii.blogging

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.pivincii.blogging.activityresult.ActivityResultObserver
import com.pivincii.blogging.activityresult.ActivityResultObservable

abstract class ActivityResultObservableActivity: AppCompatActivity(), ActivityResultObservable {
    private val activityObserverList = mutableListOf<ActivityResultObserver>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityObserverList.forEach { it.onActivityResult(requestCode, resultCode, data) }
    }

    override fun addObserver(activityResultObserver: ActivityResultObserver) {
        activityObserverList.add(activityResultObserver)
    }

    override fun removeObserver(activityResultObserver: ActivityResultObserver) {
        activityObserverList.remove(activityResultObserver)
    }

}