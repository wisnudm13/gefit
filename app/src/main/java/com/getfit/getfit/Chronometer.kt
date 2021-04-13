package com.getfit.getfit

import androidx.appcompat.app.AppCompatActivity
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_chronometer.*

class Chronometer : AppCompatActivity() {
    var isRunning = false
    var count: Long = -1
    var secondToRemind = 0
    var second = 0
    var pauseOffset: Long = 0
    var reminderClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chronometer)

        val pauseButton = button_pause
        val startButton = button_start
        val resetButton = button_reset
        //val saveButton = button_save
        val reminderButton = button_reminder
        val radioGroup = radio_group
        val oneMinRadio = oneMin_radio
        val fiveMinRadio = fiveMin_radio
        val fifteenRadio = fifteenMin_radio
        val tenMinRadio = tenMin_radio
        val chronometerText = chronometer_text
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        resetButton.visibility = View.INVISIBLE
        pauseButton.visibility = View.INVISIBLE
        //saveButton.visibility = View.INVISIBLE

        startButton.setOnClickListener {
            startChronometer()
            startButton.visibility = View.INVISIBLE
            resetButton.visibility = View.VISIBLE
            pauseButton.visibility = View.VISIBLE
            //saveButton.visibility = View.VISIBLE
            reminderButton.visibility = View.INVISIBLE
            radioGroup.visibility = View.INVISIBLE

        }

        chronometerText.setOnChronometerTickListener {
            secondCount()
            if(reminderClicked) {
                if(second != 0) {
                    if(second % secondToRemind == 0) {
                        Toast.makeText(this@Chronometer, "Finish", Toast.LENGTH_SHORT).show()
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))

//                    if (Build.VERSION.SDK_INT >= 26) {
//                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
//                    } else {
//                        vibrator.vibrate(500)
//                    }
                    }
                }

            }
        }

        pauseButton.setOnClickListener {
            if(pauseButton.text.equals("Pause")) {
                pauseButton.text = "Resume"
                pauseChronometer()
                //saveButton.visibility = View.VISIBLE

            } else {
                pauseButton.text = "Pause"
                startChronometer()
                //saveButton.visibility = View.VISIBLE
            }

        }

        resetButton.setOnClickListener {
            resetChronometer()
        }

        reminderButton.setOnClickListener {
            reminderClicked = true

            if(oneMinRadio.isChecked) {
                Toast.makeText(this@Chronometer, "Reminder set to 1 minute", Toast.LENGTH_SHORT).show()
                secondToRemind = 60

            } else if(fiveMinRadio.isChecked) {
                Toast.makeText(this@Chronometer, "Reminder set to 5 minute", Toast.LENGTH_SHORT).show()
                secondToRemind = 60 * 5

            } else if(tenMinRadio.isChecked) {
                Toast.makeText(this@Chronometer, "Reminder set to 10 minute", Toast.LENGTH_SHORT).show()
                secondToRemind = 60 * 10

            } else if(fifteenRadio.isChecked) {
                Toast.makeText(this@Chronometer, "Reminder set to 15 minute", Toast.LENGTH_SHORT).show()
                secondToRemind = 60

            }
        }

    }

    fun startChronometer() {
        val chronometer = chronometer_text
        if (!isRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset)
            chronometer.start()
            isRunning = true
        }
    }

    fun pauseChronometer() {
        val chronometer = chronometer_text
        if (isRunning) {
            chronometer.stop()
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            isRunning = false
        }
    }

    fun secondCount() {
        if(isRunning) {
            second++
        }
    }

    fun resetChronometer() {
        button_start.visibility = View.VISIBLE
        button_pause.visibility = View.INVISIBLE
        button_reminder.visibility = View.VISIBLE
        button_reset.visibility = View.INVISIBLE
        radio_group.visibility = View.VISIBLE
        button_pause.text = "Pause"
        second = 0
        pauseOffset = 0
        isRunning = false
        reminderClicked = false
        radio_group.clearCheck()
        chronometer_text.stop()
        chronometer_text.base = SystemClock.elapsedRealtime()
    }
}