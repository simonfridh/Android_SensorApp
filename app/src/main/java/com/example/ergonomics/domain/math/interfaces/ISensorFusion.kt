package com.example.ergonomics.domain.math.interfaces

interface ISensorFusion {
    operator fun invoke(alpha:Float, accelerometerAngle: Float, gyroscopeAngle: Float): Float
}