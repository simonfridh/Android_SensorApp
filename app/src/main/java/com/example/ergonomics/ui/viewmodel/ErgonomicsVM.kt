package com.example.ergonomics.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ergonomics.domain.repository.IRepository
import com.example.ergonomics.domain.repository.ISensorRepository
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
    private val repository: IRepository,
    private val sensorRepository: ISensorRepository
): IErgonomicsVM,  ViewModel() {
    private val _sensorState = MutableStateFlow(SensorState())
    override val sensorState: StateFlow<SensorState>
        get() = _sensorState


    init{
        if(sensorRepository.sensorsAvailable()) {
            sensorRepository.startSensors()

            sensorRepository.setSensorsOnChange (

                accelerometerOnChange = { x, y, z ->
                    _sensorState.value = _sensorState.value.copy(accelerometerValues = AccelerometerValues(x,y,z))
                }

            )
        }
    }

    override fun doTest() {

        repository.test()

    }
}

data class SensorState(
    val accelerometerValues: AccelerometerValues = AccelerometerValues()
)

data class AccelerometerValues(
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f
)


//Used for previews
class FakeVM: IErgonomicsVM {
    override val sensorState: StateFlow<SensorState>
        get() = MutableStateFlow(SensorState())

    override fun doTest() {}
}