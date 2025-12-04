package com.example.ergonomics.domain.usecase

import com.example.ergonomics.domain.models.SensorValues

class CalculateAngleMock: ICalculateAngleUseCase {

    /*
    Invoke makes it possible to call "CalculateAngleUseCase" objects like normal functions
    For example CalculateAngleUseCase(accelerometer, gyroscope).
    It works a bit like static functions but written like this so we can swap them with Hilt
    */
    override operator fun invoke(accelerometer: SensorValues, gyroscope: SensorValues): Float {
        //TODO calculate angle here

        return (accelerometer.y + gyroscope.x)
    }
}