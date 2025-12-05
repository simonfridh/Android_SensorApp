package com.example.ergonomics.data.sensor

import com.example.ergonomics.domain.repository.ISensorRepository
import javax.inject.Inject

class SensorRepositoryImpl @Inject constructor(
    private val accelerometer: AccelerometerSensor,
    private val gyroscope: GyroscopeSensor
): ISensorRepository {
    override fun sensorsAvailable(): Boolean {
        return accelerometer.isAvailable() && gyroscope.isAvailable()
    }

    override fun startSensors() {
        accelerometer.startListening()
        gyroscope.startListening()
    }

    override fun stopSensors() {
        accelerometer.stopListening()
        gyroscope.stopListening()
    }

    override fun setSensorsOnChange(
        accelerometerOnChange: (Float, Float, Float) -> Unit,
        gyroscopeOnChange: (Float, Float, Float, Float) -> Unit
    ) {
        accelerometer.setOnValuesChangedListener(accelerometerOnChange)
        gyroscope.setOnValuesChangedListener(gyroscopeOnChange)
    }


}