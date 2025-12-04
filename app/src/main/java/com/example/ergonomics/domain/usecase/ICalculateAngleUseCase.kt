package com.example.ergonomics.domain.usecase

import com.example.ergonomics.domain.models.SensorValues

interface ICalculateAngleUseCase {
    operator fun invoke(accelerometer: SensorValues, gyroscope: SensorValues): Float
}