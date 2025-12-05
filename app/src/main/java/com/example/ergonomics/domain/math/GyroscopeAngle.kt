package com.example.ergonomics.domain.math

import com.example.ergonomics.domain.math.interfaces.IGyroscopeAngle
import com.example.ergonomics.domain.models.SensorValue

class GyroscopeAngle: IGyroscopeAngle {

    override operator fun invoke(sensorValue: SensorValue, previousAngle: Float, dt: Float): Float {
        val x = -sensorValue.x

        val deltaDegrees = Math.toDegrees((x * dt).toDouble())
        val gyroscopeAngle = (previousAngle + deltaDegrees).toFloat()

        if (gyroscopeAngle > 180) return gyroscopeAngle - 360f
        if (gyroscopeAngle < -180) return gyroscopeAngle + 360f
        return gyroscopeAngle
    }
}