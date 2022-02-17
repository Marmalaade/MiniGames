package com.example.yummyclicker

import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class InGamTimer(lifecycle: Lifecycle) : LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    var secondCount = 0
    private var handler = Handler()
    private lateinit var runnable: Runnable

    @SuppressLint("LogNotTimber")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer() {
        runnable = Runnable {
            secondCount++
            Timber.i("Timer is at : $secondCount")
            Log.d("timer", "Timer is at : $secondCount")
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer() {
        handler.removeCallbacks(runnable)
    }
}