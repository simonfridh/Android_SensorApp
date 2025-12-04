package com.example.ergonomics.di

import com.example.ergonomics.data.sensor.AccelerometerSensor
import com.example.ergonomics.data.sensor.GyroscopeSensor
import com.example.ergonomics.data.sensor.SensorRepositoryImpl
import com.example.ergonomics.domain.repository.ISensorRepository
import com.example.ergonomics.domain.usecase.CalculateAngleMock
import com.example.ergonomics.domain.usecase.ICalculateAngleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //ISensorRepository
    @Provides
    @Singleton
    fun provideSensorRepository(accelerometerSensor: AccelerometerSensor, gyroscopeSensor: GyroscopeSensor): ISensorRepository {
        return SensorRepositoryImpl(
            accelerometer = accelerometerSensor,
            gyroscope = gyroscopeSensor
        )
    }

    @Provides
    @Singleton
    fun provideCalculateAngleUseCase(): ICalculateAngleUseCase {
        return CalculateAngleMock()
    }

}