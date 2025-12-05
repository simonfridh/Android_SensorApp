package com.example.ergonomics.domain.math

import com.example.ergonomics.domain.math.interfaces.IGyroscopeAngle
import com.example.ergonomics.domain.models.SensorValue

class GyroscopeAngle: IGyroscopeAngle {

    override operator fun invoke(sensorValue: SensorValue, previousAngle: Float, timeInterval: Float): Float {
        val x = -sensorValue.x

        val deltaDegrees = Math.toDegrees((x * timeInterval).toDouble())
        val gyroscopeAngle = previousAngle + deltaDegrees

        return gyroscopeAngle.toFloat()
    }
}