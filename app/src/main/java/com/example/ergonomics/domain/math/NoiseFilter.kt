package com.example.ergonomics.domain.math

import com.example.ergonomics.domain.math.interfaces.INoiseFilter

class NoiseFilter: INoiseFilter {

    override operator fun invoke(alpha: Float, currentAngle: Float, previousFilteredAngle: Float): Float {
        return (alpha*currentAngle) + ((1-alpha) * previousFilteredAngle)
    }
}