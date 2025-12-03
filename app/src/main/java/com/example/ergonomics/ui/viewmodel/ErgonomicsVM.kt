package com.example.ergonomics.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            sensorRepository.startSensors()

            sensorRepository.setSensorsOnChange (
                accelerometerOnChange = { x, y, z ->
                    _accelerometerValues.value = SensorValues(x,y,z)
                }

            )
        }
    }

    override fun startMeasurement() {
        job?.cancel()  // Cancel any existing measurement

        job = viewModelScope.launch {
            for(i in 0 until 30) { //30sec
                delay(1000)
                _sensorState.value = _sensorState.value.copy(angle = _accelerometerValues.value.y)
            }
        }
    }

    override fun stopMeasurement() {
        job?.cancel()  // Cancel any existing measurement
    }
}



data class SensorState(
    val angle: Float = 0f
)

data class SensorValues(
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f
)



//Used for previews
class FakeVM: IErgonomicsVM {
    override val sensorState: StateFlow<SensorState>
        get() = MutableStateFlow(SensorState())

    override fun startMeasurement() {}

    override fun stopMeasurement() {}

}