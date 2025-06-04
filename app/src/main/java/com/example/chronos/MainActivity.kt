package com.example.chronos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    lateinit var btnPlay: ImageButton
    lateinit var btnStop: ImageButton
    lateinit var chronometer: Chronometer
    lateinit var pointer: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay = findViewById(R.id.imgbutton_play)
        btnStop = findViewById(R.id.imgbutton_stop)
        chronometer = findViewById(R.id.chronometer)
        pointer = findViewById(R.id.imgview_pointer)

        var chronos: Boolean = true
        var lastTime: Long? = null
        var firstRun: Boolean = true
        var tick: Int = 0
        var dontTrigger = false


        btnPlay.setOnClickListener {
            if (firstRun) {
                chronometer.base = SystemClock.elapsedRealtime()
                firstRun = false
                tick = 0
                dontTrigger = true
            } else if (chronos && !firstRun){
                dontTrigger = true
            } else {
                tick = tick - 6
            }

            if (chronos){
                if (lastTime != null){
                    dontTrigger = true
                    chronometer.base = SystemClock.elapsedRealtime() - lastTime!!
                }
                chronometer.start()
                btnPlay.setImageResource(R.drawable.ic_pause)
                chronos = false

            } else{
                chronometer.stop()
                //tick = tick - 6
                lastTime = SystemClock.elapsedRealtime() - chronometer.base
                btnPlay.setImageResource(R.drawable.ic_play)
                chronos = true
            }

        }

        chronometer.setOnChronometerTickListener {
            if(dontTrigger){
                dontTrigger = false
            }
            else {
                Log.d("pepino", tick.toString())
                tick = tick + 6
            }

            pointer.rotation = tick.toFloat()

        }

        btnStop.setOnClickListener {
            firstRun = true
            tick = 0
            chronometer.stop()
            dontTrigger = true
            chronometer.base = SystemClock.elapsedRealtime()
            btnPlay.setImageResource(R.drawable.ic_play)
            chronos = true
            lastTime = null
        }


    }
}