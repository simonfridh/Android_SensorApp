package com.example.ergonomics.di

import android.app.Application
import com.example.ergonomics.data.sensor.LightSensor
import com.example.ergonomics.data.sensor.MeasurableSensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    //Sensor
    @Provides
    @Singleton
    fun provideLightSensor(app: Application): MeasurableSensor{
        return LightSensor(app)
    }


}