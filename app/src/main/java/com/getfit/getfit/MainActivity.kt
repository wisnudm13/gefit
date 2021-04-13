package com.getfit.getfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardViewStopwatch.setOnClickListener{
            val intent = Intent(this, StopwatchActivity::class.java)
            startActivity(intent)
        }

        cardViewTimer.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }

        cardViewJump.setOnClickListener {
            val intent = Intent(this, JumpActivity::class.java)
            startActivity(intent)
        }

        cardViewStepCounter.setOnClickListener {
            val intent = Intent(this, StepActivity::class.java)
            startActivity(intent)
        }
    }
}