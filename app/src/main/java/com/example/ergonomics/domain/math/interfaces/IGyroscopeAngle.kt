package com.example.ergonomics.domain.math.interfaces

import com.example.ergonomics.domain.models.SensorValue

interface IGyroscopeAngle {
    operator fun invoke(sensorValue: SensorValue, previousAngle: Float, dt: Float): Float
}