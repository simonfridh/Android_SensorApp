package com.example.ergonomics.domain.math

import com.example.ergonomics.domain.math.interfaces.IAccelerometerAngle
import com.example.ergonomics.domain.models.SensorValue
import kotlin.math.atan2

class AccelerometerAngle: IAccelerometerAngle {

    override operator fun invoke(sensorValue: SensorValue): Float {
        val y = -sensorValue.y.toDouble()
        val z = -sensorValue.z.toDouble()

        val angleRad = atan2(z,y)
        val angleDegrees = Math.toDegrees(angleRad).toFloat()

        return angleDegrees
    }
}