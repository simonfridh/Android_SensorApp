package com.example.ergonomics.domain.repository

interface ISensorRepository {

    fun sensorsAvailable(): Boolean

    fun startSensors()
    fun stopSensors()

    fun setSensorsOnChange(
        accelerometerOnChange: (Float,Float,Float) -> Unit,
        //TODO gyroscope on change
    )
}