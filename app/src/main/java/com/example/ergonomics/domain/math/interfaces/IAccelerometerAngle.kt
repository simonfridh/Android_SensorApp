package com.example.ergonomics.domain.math.interfaces

import com.example.ergonomics.domain.models.SensorValue

interface IAccelerometerAngle {
    operator fun invoke(sensorValue: SensorValue): Float
}