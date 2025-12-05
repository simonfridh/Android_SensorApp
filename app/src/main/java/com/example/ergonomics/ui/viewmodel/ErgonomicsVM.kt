package com.example.ergonomics.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ergonomics.domain.models.Measurement
import com.example.ergonomics.domain.models.SensorValue
import com.example.ergonomics.domain.repository.ISensorRepository
import com.example.ergonomics.domain.math.interfaces.IAccelerometerAngle
import com.example.ergonomics.domain.math.interfaces.IGyroscopeAngle
import com.example.ergonomics.domain.math.interfaces.INoiseFilter
import com.example.ergonomics.domain.math.interfaces.ISensorFusion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface IErgonomicsVM {
    val measurementState: StateFlow<MeasurementState>
    fun startMeasurement()
    fun stopMeasurement()
}

@HiltViewModel
class ErgonomicsVM @Inject constructor(
    private val sensorRepository: ISensorRepository,
    private val accelerometerAngle: IAccelerometerAngle,
    private val gyroscopeAngle: IGyroscopeAngle,
    private val noiseFilter: INoiseFilter,
    private val sensorFusion: ISensorFusion
): IErgonomicsVM,  ViewModel() {
    private val _measurementState = MutableStateFlow(MeasurementState())
    override val measurementState: StateFlow<MeasurementState>
        get() = _measurementState

    private var _accelerometerValues = mutableStateOf(SensorValue())
    private var _gyroscopeValues = mutableStateOf(SensorValue())
    private var job: Job? = null  // coroutine job for the measurement
    private val measurementTime = 30 //TODO hardcoded time in seconds

    override fun startMeasurement() {
        stopMeasurement()
        _measurementState.value = _measurementState.value.copy(measurementSummary = emptyList())

        //Set up sensors before measurement
        if (sensorRepository.sensorsAvailable()) {
            sensorRepository.startSensors()
            sensorRepository.setSensorsOnChange (
                accelerometerOnChange = { x, y, z ->
                    _accelerometerValues.value = SensorValue(x,y,z)
                },
                gyroscopeOnChange = { x, y, z ->
                    _gyroscopeValues.value = SensorValue(x,y,z)

                }
            )
        }

        //Start timed measurement
        job = viewModelScope.launch {
            //running = true
            _measurementState.value = _measurementState.value.copy(measurementRunning = true)
            measurementLoop()
            stopMeasurement()
        }
    }

    override fun stopMeasurement() {
        job?.cancel()
        sensorRepository.stopSensors()
        _measurementState.value = _measurementState.value.copy(measurementRunning = false)
    }

    private suspend fun measurementLoop() {
        //Setting some starting values for the n-1 values
        var previousFilteredAngle = accelerometerAngle(_accelerometerValues.value)
        var previousGyroscopeAngle = accelerometerAngle(_accelerometerValues.value)

        //one loop is 0.1sec
        for(i in 0 until measurementTime * 10) {
            delay(100)
            val currentTime = (i+1).toFloat() / 10f //Current time in seconds

            //Algorithm 1 - Linear Acceleration
            val accelerometerAngle = accelerometerAngle(_accelerometerValues.value)
            val filteredAngle = noiseFilter(0.8f, accelerometerAngle, previousFilteredAngle)

            //Algorithm 2 - Sensor Fusion TODO Funkar inte alls
            //val gyroscopeAngle = gyroscopeAngle(_gyroscopeValues.value, previousGyroscopeAngle, 0.1f)
            //val fusionAngle = sensorFusion(0.8f, filteredAngle, gyroscopeAngle)

            //save values of n-1 for next loop
            previousFilteredAngle = filteredAngle
            //previousGyroscopeAngle = gyroscopeAngle

            _measurementState.value = _measurementState.value.copy(
                totalTime = currentTime,
                currentAngle = filteredAngle,
                measurementSummary = _measurementState.value.measurementSummary + Measurement(filteredAngle, currentTime)
            )
        }
    }
}

data class MeasurementState(
    val measurementRunning: Boolean = false,
    val totalTime: Float = 0f,
    val currentAngle: Float = 0f,
    val measurementSummary: List<Measurement> = emptyList()
)



//Used for previews
class FakeVM: IErgonomicsVM {
    override val measurementState: StateFlow<MeasurementState>
        get() = MutableStateFlow(MeasurementState())
    override fun startMeasurement() {}
    override fun stopMeasurement() {}
}