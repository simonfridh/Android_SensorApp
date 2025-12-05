package com.example.ergonomics.domain.math.interfaces

interface INoiseFilter {
    operator fun invoke(alpha: Float, currentAngle: Float, previousFilteredAngle: Float): Float
}