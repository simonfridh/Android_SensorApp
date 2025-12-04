package com.example.ergonomics.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ergonomics.domain.models.SensorValues
import com.example.ergonomics.domain.repository.ISensorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface IErgonomicsVM {
    val sensorState: StateFlow<SensorState>

    fun startMeasurement()
    fun stopMeasurement()
}

@HiltViewModel
class ErgonomicsVM @Inject constructor(
    private val sensorRepository: ISensorRepository
): IErgonomicsVM,  ViewModel() {
    private val _sensorState = MutableStateFlow(SensorState())
    override val sensorState: StateFlow<SensorState>
        get() = _sensorState

    private var _accelerometerValues = mutableStateOf(SensorValues())
    private var _gyroscopeValues = mutableStateOf(SensorValues())
    private var job: Job? = null  // coroutine job for the measurement

    init{
        if(sensorRepository.sensorsAvailable()) {

        }
    }

    override fun startMeasurement() {
        stopMeasurement()

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
            _sensorState.value = _sensorState.value.copy(measurementRunning = true)
            for(i in 0 until 300) { //30sec
                delay(100)
                _sensorState.value = _sensorState.value.copy(currentAngle = _gyroscopeValues.value.y)
            }
            stopMeasurement()
        }
    }

    override fun stopMeasurement() {
        job?.cancel()
        sensorRepository.stopSensors()
        _sensorState.value = _sensorState.value.copy(measurementRunning = false)
    }
}

data class SensorState(
    val measurementRunning: Boolean = false,
    val currentAngle: Float = 0f
)



//Used for previews
class FakeVM: IErgonomicsVM {
    override val sensorState: StateFlow<SensorState>
        get() = MutableStateFlow(SensorState())

    override fun startMeasurement() {}

    override fun stopMeasurement() {}

}