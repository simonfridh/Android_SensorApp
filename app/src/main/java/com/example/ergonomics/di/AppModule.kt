package com.example.ergonomics.di

import com.example.ergonomics.data.sensor.AccelerometerSensor
import com.example.ergonomics.data.sensor.GyroscopeSensor
import com.example.ergonomics.data.sensor.SensorRepositoryImpl
import com.example.ergonomics.domain.repository.ISensorRepository
import com.example.ergonomics.domain.math.AccelerometerAngle
import com.example.ergonomics.domain.math.GyroscopeAngle
import com.example.ergonomics.domain.math.interfaces.IAccelerometerAngle
import com.example.ergonomics.domain.math.NoiseFilter
import com.example.ergonomics.domain.math.SensorFusion
import com.example.ergonomics.domain.math.interfaces.IGyroscopeAngle
import com.example.ergonomics.domain.math.interfaces.INoiseFilter
import com.example.ergonomics.domain.math.interfaces.ISensorFusion
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
    fun provideAccelerometerAngle(): IAccelerometerAngle {
        return AccelerometerAngle()
    }

    @Provides
    @Singleton
    fun provideGyroscopeAngle(): IGyroscopeAngle {
        return GyroscopeAngle()
    }

    @Provides
    @Singleton
    fun provideNoiseFilter(): INoiseFilter {
        return NoiseFilter()
    }

    @Provides
    @Singleton
    fun provideSensorFusion(): ISensorFusion {
        return SensorFusion()
    }
}