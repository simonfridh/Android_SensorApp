package com.example.ergonomics.data.sensor

import com.example.ergonomics.domain.repository.ISensorRepository
import javax.inject.Inject

class SensorRepositoryImpl @Inject constructor(
    private val accelerometer: AccelerometerSensor
): ISensorRepository {
    override fun sensorsAvailable(): Boolean {
        return accelerometer.isAvailable()
    }

    override fun startSensors() {
        accelerometer.startListening()
    }

    override fun stopSensors() {
        accelerometer.stopListening()
    }

    override fun setSensorsOnChange(accelerometerOnChange: (Float, Float, Float) -> Unit) {
        accelerometer.setOnValuesChangedListener(accelerometerOnChange)
    }
}