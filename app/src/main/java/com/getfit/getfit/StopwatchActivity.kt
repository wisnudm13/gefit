package com.getfit.getfit

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stopwatch.*

class StopwatchActivity : AppCompatActivity() {
    var isRunning = false
    var startTime: Long = 0; var timeInMiliSecond : Long = 0; var timeSwapBuff : Long = 0; var updateTime : Long = 0
    var customHandler = Handler()

    var updateTimerThread = object: Runnable {
        override fun run() {
            timeInMiliSecond = SystemClock.uptimeMillis() - startTime
            updateTime = timeSwapBuff + timeInMiliSecond
            var second = updateTime / 1000
            var minute = second.toInt() / 60
            second %= 60
            var miliSecond = updateTime % 1000
            text_stopwatch.setText("" + String.format("%02d", minute) + ":" + String.format("%02d", second) + ":" + String.format("%03d", miliSecond))

            customHandler.postDelayed(this, 0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        button_left.visibility = View.GONE
        button_right.visibility = View.GONE

        button_center.setOnClickListener {
            button_center.visibility = View.GONE
            button_left.visibility = View.VISIBLE
            button_right.visibility = View.VISIBLE
            startStopWatch()
        }

        button_left.setOnClickListener {
            if(isRunning) {
                button_left.setText("Resume")
                //button_right.setText("Reset")
                pauseStopWatch()

            } else {
                button_left.setText("Pause")
                //button_right.setText("Reset")
                //button_right.setText("Lap")
                startStopWatch()
            }

        }

        button_right.setOnClickListener {
            if(button_right.text.equals("Lap")) {
                val inflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val addView = inflater.inflate(R.layout.row, null)
                var textContent = addView.findViewById<TextView>(R.id.text_content)
                textContent.setText(text_stopwatch.text)
                //lap_container.addView(addView)
                //scroll.fullScroll(View.FOCUS_DOWN)
                //startStopWatch()

            } else {
                pauseStopWatch()
                resetStopWatch()
            }


        }


    }

    fun startStopWatch() {
        startTime = SystemClock.uptimeMillis()
        customHandler.postDelayed(updateTimerThread, 0)
        isRunning = true
    }

    fun pauseStopWatch() {
        timeSwapBuff += timeInMiliSecond
        customHandler.removeCallbacks(updateTimerThread)
        isRunning = false
    }

    fun resetStopWatch() {
        button_center.visibility = View.VISIBLE
        button_left.visibility = View.GONE
        button_right.visibility = View.GONE
        button_left.text = "Pause"
        //button_right.text = "Lap"
        button_right.text = "Reset"
        text_stopwatch.text = "00:00:000"
        startTime = 0
        timeInMiliSecond = 0
        timeSwapBuff = 0
        updateTime = 0
        isRunning = false
        //lap_container.removeAllViews()
    }


}