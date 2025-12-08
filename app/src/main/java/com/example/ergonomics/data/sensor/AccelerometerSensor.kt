package com.example.ergonomics.data.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class AccelerometerSensor(
    context: Context
): SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var onValueChanged: ((x:Float, y:Float, z:Float) -> Unit)? = null


    // Can be used by viewmodel to set what function should be called when sensor updates
    fun setOnValuesChangedListener(listener: (x:Float, y:Float, z:Float) -> Unit) {
        onValueChanged = listener
    }

    // Checks if Sensor is available. Can be called from VM
    fun isAvailable(): Boolean {
        return accelerometer != null
    }

    // Start listening to the sensor
    fun startListening() {
        if( !isAvailable() ) return
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    // Stop listening to the sensor
    fun stopListening() {
        sensorManager.unregisterListener(this)
    }


    // Built in function from SensorEventListener. This runs everytime the sensor updates
    // This calls the function we send here from the viewmodel
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            onValueChanged?.invoke(
                event.values[0],
                event.values[1],
                event.values[2]
            )
        }
    }

    //Must exist but doesn't do anything
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}