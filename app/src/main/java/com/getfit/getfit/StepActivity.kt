package com.getfit.getfit

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_step.*

class StepActivity : AppCompatActivity() {
    var flag = false
    var isRun = false
    var previousTotalStep = 0f
    var stepCount = 0f

    val ACTIVITY_RECOGNITION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)

        text_step.setText("0")
        //resetStepCounter()
        startStepCounter()
//        val permission =  ContextCompat.checkSelfPermission(this@StepActivity, android.Manifest.permission.ACTIVITY_RECOGNITION)
//
//        if(permission != PackageManager.PERMISSION_GRANTED) {
//            requestPermission()
//
//        } else {
//            Toast.makeText(this@StepActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
//
//        }

        //val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //button_left.visibility = View.INVISIBLE
        //button_right.visibility = View.INVISIBLE

        if(flag) {

        } else {
            flag = true
        }

        button_right.setOnClickListener {
            //button_left.visibility = View.INVISIBLE
            //button_right.visibility = View.INVISIBLE
            //button_center.visibility = View.VISIBLE
            text_step.setText("0")
            stopStepCounter()
            resetStepCounter()
            startStepCounter()
        }

//        button_center.setOnClickListener {
//            button_center.visibility = View.GONE
//            button_left.visibility = View.VISIBLE
//            button_right.visibility = View.VISIBLE
//
//
//        }

//        button_left.setOnClickListener {
//            if(button_left.text.equals("Pause")) {
//                stopStepCounter()
//                //button_left.setText("Resume")
//
//            } else {
//                startStepCounter()
//                //button_left.setText("Pause")
//            }
//        }

    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null) {
                if(isRun) {

                    //stepCount = event.values[0]

//                    if(!flag) {
//                        previousTotalStep = event.values[0]
//                        text_step.setText(0)
//                    } else {
//                        previousTotalStep = 0f
//                    }
                    val currentStep = stepCount.toInt() - previousTotalStep.toInt()
                    //Toast.makeText(this@StepActivity, "berhasil", Toast.LENGTH_SHORT).show()
                    stepCount++
                    text_step.setText(stepCount.toInt().toString())
                }

//                var accel_x = event.values[0]
//                var accel_y = event.values[1]
//                var accel_z = event.values[2]
//
//                var magnitude: Double = sqrt(accel_x * accel_x + accel_y * accel_y + accel_z * accel_z).toDouble()
//                var deltaMagnitude = magnitude - previousMagnitude
//                previousMagnitude = magnitude
//
//                if(deltaMagnitude > 6) {
//                    stepCount++
//                }
//

//
            }



        }
    }

    fun stopStepCounter() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(sensorListener)
    }

    fun startStepCounter() {
        //resetStepCounter()
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
                sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
                SensorManager.SENSOR_DELAY_FASTEST
        )

        isRun = true
    }

    fun resetStepCounter() {
        previousTotalStep = stepCount
        stepCount = 0f
        text_step.setText(0.toString())

    }

//
//    fun stopStepCounter() {
//        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        sensorManager.unregisterListener(sensorListener)
//    }

//    fun startStepCounter() {
//        isRun = true
//        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//
//        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
//        //val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//
//        if(stepSensor != null) {
//            //Toast.makeText(this@StepActivity, "berhasil", Toast.LENGTH_SHORT).show()
//
//
//        } else {
//            //Toast.makeText(this@StepActivity, "no sensor", Toast.LENGTH_SHORT).show()
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        isRun = true
//        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//
//        if(stepSensor != null) {
//            //Toast.makeText(this@StepActivity, "berhasil", Toast.LENGTH_SHORT).show()
//            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
//
//        } else {
//            //Toast.makeText(this@StepActivity, "no sensor", Toast.LENGTH_SHORT).show()
//        }
//
//    }

//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        Toast.makeText(this@StepActivity, "aaa", Toast.LENGTH_SHORT).show()
//
//        if(isRun) {
//            totalSteps = event!!.values[0]
//            val currentStep = totalSteps.toInt() - previousTotalStep.toInt()
//            text_step.setText(currentStep)
//
//        }
//    }

//    fun requestPermission() {
//        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACTIVITY_RECOGNITION)) {
//            AlertDialog.Builder(this)
//                    .setTitle("Permission Needed")
//                    .setMessage("This Permission is used to track your step")
//                    .setPositiveButton("OK") { dialog, which ->
//                        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION), ACTIVITY_RECOGNITION_CODE)
//                    }
//                    .setNegativeButton("Cancel") { dialog, which ->
//                        dialog.dismiss()
//                    }
//                    .create().show()
//        } else {
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION), ACTIVITY_RECOGNITION_CODE)
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        if(requestCode == ACTIVITY_RECOGNITION_CODE) {
//            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this@StepActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
//
//            } else {
//                Toast.makeText(this@StepActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


}