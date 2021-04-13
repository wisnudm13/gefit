package com.getfit.getfit

import android.graphics.Color
import android.os.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.concurrent.TimeUnit

class TimerActivity : AppCompatActivity() {
    var isRunning = false
    lateinit var countDownTimer: CountDownTimer

    var startTimeInMillisecond: Long = 0//600000
    var timeLeftInMillisecond: Long = startTimeInMillisecond
    var hourInMilliSeconds = 0L
    var minuteInMilliSeconds = 0L
    var secondInMilliSeconds = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        startActivity()

        number_picker_hour.setOnValueChangedListener { picker, oldVal, newVal ->
            hourInMilliSeconds = newVal.toLong() * 3600000
        }

        number_picker_minute.setOnValueChangedListener { picker, oldVal, newVal ->
            minuteInMilliSeconds = newVal.toLong() * 60000
        }

        number_picker_second.setOnValueChangedListener { picker, oldVal, newVal ->
            secondInMilliSeconds = newVal.toLong() * 1000
        }

        button_center.setOnClickListener {
            if(secondInMilliSeconds == 0L && minuteInMilliSeconds == 0L && hourInMilliSeconds == 0L) {
                Toast.makeText(this@TimerActivity, "Please Input the timer", Toast.LENGTH_SHORT).show()

            } else {
                button_center.visibility = View.GONE
                button_left.visibility = View.VISIBLE
                button_right.visibility = View.VISIBLE
                text_hour.visibility = View.INVISIBLE
                text_minute.visibility = View.INVISIBLE
                text_second.visibility = View.INVISIBLE
                number_picker_hour.visibility = View.INVISIBLE
                number_picker_minute.visibility = View.INVISIBLE
                number_picker_second.visibility = View.INVISIBLE
                setTime(hourInMilliSeconds + minuteInMilliSeconds + secondInMilliSeconds)
                startTimer()
            }


        }

        button_left.setOnClickListener {
            if(button_left.text.equals("Pause")) {
                button_left.setText("Resume")
                pauseTimer()

            } else {
                button_left.setText("Pause")
                startTimer()
            }

        }

        button_right.setOnClickListener {
            resetTimer()
        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    fun setTime(milliseconds : Long) {
        startTimeInMillisecond = milliseconds + 300
        timeLeftInMillisecond = startTimeInMillisecond
    }

    fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillisecond, 1000) {
            override fun onFinish() {
                val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
                Toast.makeText(this@TimerActivity, "Finish", Toast.LENGTH_SHORT).show()
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                resetTimer()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillisecond = millisUntilFinished
                updateCountDownText()
            }

        }.start()

        isRunning = true
    }

    fun startActivity() {
        button_left.visibility = View.GONE
        button_right.visibility = View.GONE

        number_picker_hour.maxValue = 24
        number_picker_minute.maxValue = 60
        number_picker_second.maxValue = 60

        number_picker_hour.minValue = 0
        number_picker_minute.minValue = 0
        number_picker_second.minValue = 0

        number_picker_hour.textSize = 50f
        number_picker_minute.textSize = 50f
        number_picker_second.textSize = 50f

        number_picker_hour.textColor = Color.WHITE
        number_picker_minute.textColor = Color.WHITE
        number_picker_second.textColor = Color.WHITE
    }

    fun updateCountDownText() {
//        val s: Long = seconds % 60
//        val m: Long = seconds / 60 % 60
//        val h: Long = seconds / (60 * 60) % 24

        val hours = TimeUnit.MILLISECONDS.toHours(timeLeftInMillisecond)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeftInMillisecond) % TimeUnit.HOURS.toMinutes(1)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeftInMillisecond) % TimeUnit.MINUTES.toSeconds(1)

        val timeLeftFormatted = String.format("%02d:%02d:%02d", hours.toInt(), minutes.toInt(), seconds.toInt())

        text_timer.setText(timeLeftFormatted)
    }

    fun pauseTimer() {
        countDownTimer.cancel()
        isRunning = false
    }

    fun resetTimer() {
        timeLeftInMillisecond = startTimeInMillisecond
        text_timer.setText("00:00:00")
        button_left.visibility = View.GONE
        button_right.visibility = View.GONE
        button_center.visibility = View.VISIBLE
        number_picker_hour.visibility = View.VISIBLE
        number_picker_minute.visibility = View.VISIBLE
        number_picker_second.visibility = View.VISIBLE
        text_hour.visibility = View.VISIBLE
        text_minute.visibility = View.VISIBLE
        text_second.visibility = View.VISIBLE
        number_picker_hour.value = 0
        number_picker_minute.value = 0
        number_picker_second.value = 0
        hourInMilliSeconds = 0L
        minuteInMilliSeconds = 0L
        secondInMilliSeconds = 0L
        pauseTimer()
    }


}