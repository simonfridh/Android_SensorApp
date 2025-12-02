package com.example.ergonomics.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ergonomics.data.sensor.MeasurableSensor
import com.example.ergonomics.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface IErgonomicsVM {
    val sensorState: StateFlow<SensorState>
    fun doTest()

}

@HiltViewModel
class ErgonomicsVM @Inject constructor(
    private val lightSensor: MeasurableSensor,
    private val repository: IRepository
): IErgonomicsVM,  ViewModel() {
    private val _sensorState = MutableStateFlow(SensorState())
    override val sensorState: StateFlow<SensorState>
        get() = _sensorState

    var isDark by mutableStateOf(false)

    init{
        lightSensor.startListening()
        lightSensor.setSensorValuesChangedListener { values ->
            val lux = values[0]
            _sensorState.value = _sensorState.value.copy(isDark = lux < 60f)
        }
    }

    override fun doTest() {

        repository.test()

    }
}

data class SensorState(
    val isDark: Boolean = false
)


//Used for previews
class FakeVM: IErgonomicsVM {
    override val sensorState: StateFlow<SensorState>
        get() = MutableStateFlow(SensorState())

    override fun doTest() {}
}