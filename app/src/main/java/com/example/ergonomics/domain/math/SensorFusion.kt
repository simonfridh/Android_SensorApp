package com.example.ergonomics.domain.math

import com.example.ergonomics.domain.math.interfaces.ISensorFusion

class SensorFusion: ISensorFusion {

    override operator fun invoke(alpha:Float, accelerometerAngle: Float, gyroscopeAngle: Float): Float {
        return (alpha * accelerometerAngle) + ((1-alpha) * gyroscopeAngle)
    }
}