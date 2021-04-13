package com.getfit.getfit

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_jump.*
import kotlin.math.sqrt

class JumpActivity : AppCompatActivity() {
    var yLast = 0f; var yVal = 0f
    var countJump = 0
    var module = 0f
    var maxModule = 0f
    var preventDuplicate = false
    private var isJumping = false
    private var isGoingUp = false
    private var isGoingDown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jump)

        //button_left.visibility = View.INVISIBLE
        //button_right.visibility = View.INVISIBLE
        yVal = SensorManager.GRAVITY_EARTH
        yLast = SensorManager.GRAVITY_EARTH

//        button_left.setOnClickListener {
//            if(button_left.text.equals("Pause")) {
//                stopJumpCounter()
//                button_left.setText("Resume")
//
//            } else {
//                startJumpCounter()
//                button_left.setText("Pause")
//            }
//        }

        startJumpCounter()

//        button_center.setOnClickListener {
//            button_center.visibility = View.GONE
//            button_left.visibility = View.VISIBLE
//            button_right.visibility = View.VISIBLE
//            startJumpCounter()
//        }

        button_right.setOnClickListener(View.OnClickListener {
            //button_left.visibility = View.INVISIBLE
            //button_right.visibility = View.INVISIBLE
            //button_center.visibility = View.VISIBLE
            countJump = 0
            text_jump.setText("0")
            stopJumpCounter()
            startJumpCounter()
            //stopJumpCounter()
        })


    }

    fun startJumpCounter() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            sensorListener,
//            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun stopJumpCounter() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(sensorListener)
    }

    fun checkState(module : Float) {
        val alpha = 0.30f

        if(maxModule > 38 && !isJumping) {
            isJumping = true
            isGoingUp = true
            return
        }

        val halfMaxModule = maxModule - maxModule * alpha

        if (module < halfMaxModule && isJumping && isGoingUp) {
            isGoingUp = false
            isGoingDown = true
            return
        }

        if (module < halfMaxModule && isJumping && isGoingDown) {
            isJumping = false
            isGoingDown = false
            maxModule = 0f
            countJump++
            text_jump.setText(countJump.toString())
            if (!preventDuplicate) {
                preventDuplicate = true

            }


            return
        }
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent) {

            val valueX = sensorEvent.values[0]
            val valueY = sensorEvent.values[1]
            val valueZ = sensorEvent.values[2]


            module = sqrt(valueX*valueX + valueY*valueY + valueZ*valueZ)

            if (maxModule < module) maxModule = module

            checkState(module)

//            yVal = sensorEvent.values[1]
//            if (yLast - yVal > 36 || yVal - yLast > 36) {
//                yLast = yVal
//                countJump = countJump + 1
//            }
//
//            yLast = yVal
//            text_jump.setText(countJump.toString())


        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    }
}