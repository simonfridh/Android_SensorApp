package com.example.ergonomics.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ergonomics.domain.models.Measurement
import com.example.ergonomics.domain.models.SensorValues
import com.example.ergonomics.domain.repository.ISensorRepository
import com.example.ergonomics.domain.usecase.ICalculateAngleUseCase
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
    private val calculateAngle: ICalculateAngleUseCase
): IErgonomicsVM,  ViewModel() {
    private val _measurementState = MutableStateFlow(MeasurementState())
    override val measurementState: StateFlow<MeasurementState>
        get() = _measurementState

    private var _accelerometerValues = mutableStateOf(SensorValues())
    private var _gyroscopeValues = mutableStateOf(SensorValues())
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
                    _accelerometerValues.value = SensorValues(x,y,z)
                },
                gyroscopeOnChange = { x, y, z ->
                    _gyroscopeValues.value = SensorValues(x,y,z)

                }
            )
        }

        //Start timed measurement
        job = viewModelScope.launch {
            _measurementState.value = _measurementState.value.copy(measurementRunning = true)

            //one loop is 0.1sec
            for(i in 0 until measurementTime * 10) {
                delay(100)
                val angle = calculateAngle(_accelerometerValues.value, _gyroscopeValues.value)
                val currentTime = (i+1).toFloat() / 10f //convert to seconds

                _measurementState.value = _measurementState.value.copy(
                    totalTime = currentTime,
                    currentAngle = angle,
                    measurementSummary = _measurementState.value.measurementSummary + Measurement(angle, currentTime)
                )
            }

            stopMeasurement()
        }
    }

    override fun stopMeasurement() {
        job?.cancel()
        sensorRepository.stopSensors()
        _measurementState.value = _measurementState.value.copy(measurementRunning = false)
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