package com.example.ergonomics.di

import com.example.ergonomics.data.sensor.AccelerometerSensor
import com.example.ergonomics.data.sensor.SensorRepositoryImpl
import com.example.ergonomics.domain.repository.ISensorRepository
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
    fun provideSensorRepository(accelerometerSensor: AccelerometerSensor): ISensorRepository {
        return SensorRepositoryImpl(
            accelerometer = accelerometerSensor
        )
    }

}