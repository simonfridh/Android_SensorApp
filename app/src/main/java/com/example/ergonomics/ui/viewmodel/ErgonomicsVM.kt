package com.example.ergonomics.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ergonomics.domain.models.Measurement
import com.example.ergonomics.domain.models.SensorValue
import com.example.ergonomics.domain.repository.ISensorRepository
import com.example.ergonomics.domain.math.interfaces.IAccelerometerAngle
import com.example.ergonomics.domain.math.interfaces.IGyroscopeAngle
import com.example.ergonomics.domain.math.interfaces.INoiseFilter
import com.example.ergonomics.domain.math.interfaces.ISensorFusion
import com.example.ergonomics.domain.repository.IFileExportRepository
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
    fun changeDisplayGraph()
}

@HiltViewModel
class ErgonomicsVM @Inject constructor(
    private val sensorRepository: ISensorRepository,
    private val calculateAccelerometerAngle: IAccelerometerAngle,
    private val calculateGyroscopeAngle: IGyroscopeAngle,
    private val noiseFilter: INoiseFilter,
    private val sensorFusion: ISensorFusion,
    private val fileExportRepository: IFileExportRepository
): IErgonomicsVM,  ViewModel() {
    private val _measurementState = MutableStateFlow(MeasurementState())
    override val measurementState: StateFlow<MeasurementState>
        get() = _measurementState

    private var accelerometerAngle = 0f
    private var gyroscopeAngle = 0f

    private val _measurementTime = 30
    private var job: Job? = null  // coroutine job for the measurement


    override fun startMeasurement() {
        stopMeasurement()
        _measurementState.value = _measurementState.value.copy(measurementSummary = emptyList())

        //Set up sensors before measurement
        if (sensorRepository.sensorsAvailable()) {
            sensorRepository.startSensors()
            sensorRepository.setSensorsOnChange (
                accelerometerOnChange = { x, y, z ->
                    accelerometerAngle = calculateAccelerometerAngle(SensorValue(x,y,z))
                },
                gyroscopeOnChange = { x, y, z, dt ->
                    gyroscopeAngle = calculateGyroscopeAngle(SensorValue(x,y,z),gyroscopeAngle, dt)
                }
            )
        }


        //Start timed measurement
        job = viewModelScope.launch {
            //running = true
            _measurementState.value = _measurementState.value.copy(measurementRunning = true)
            delay(300) //small delay to give sensors some time to start
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
        var previousFilteredAngle = accelerometerAngle
        gyroscopeAngle = accelerometerAngle

        //one loop is 0.05sec
        for(i in 0 until _measurementTime * 20) {
            delay(50)
            val currentTime = (i+1).toFloat() / 20f //Current time in seconds

            //Algorithm 1 - Linear Acceleration
            val filteredAngle = noiseFilter(0.5f, accelerometerAngle, previousFilteredAngle)
            //Algorithm 2 - Sensor Fusion
            val fusionAngle = sensorFusion(0.35f, filteredAngle, gyroscopeAngle)

            previousFilteredAngle = filteredAngle
            _measurementState.value = _measurementState.value.copy(
                totalTime = currentTime,
                currentAngle = fusionAngle,
                measurementSummary = _measurementState.value.measurementSummary + Measurement(fusionAngle, currentTime)
            )
        }
    }

    override fun changeDisplayGraph() {
        _measurementState.value = _measurementState.value.copy(displayGraph = !measurementState.value.displayGraph)
    }
}

data class MeasurementState(
    val measurementRunning: Boolean = false,
    val displayGraph: Boolean = true,
    val totalTime: Float = 0f,
    val currentAngle: Float = 0f,
    val measurementSummary: List<Measurement> = emptyList()
)



//Used for previews
class FakeVM: IErgonomicsVM {
    override val measurementState: StateFlow<MeasurementState>
        get() = MutableStateFlow(
            MeasurementState(
                measurementSummary = listOf(
                    Measurement(-180f,  0.05f),
                    Measurement(-180f,  0.10f),
                    Measurement(-90f,  0.15f),
                    Measurement(-90f,  0.20f),
                    Measurement(0f,  0.25f),
                    Measurement(0f,  0.30f),
                    Measurement(90f,  0.35f),
                    Measurement(90f,  0.40f),
                    Measurement(180f,  0.45f),
                    Measurement(180f,  0.50f),
                )
            )
        )

    override fun startMeasurement() {}
    override fun stopMeasurement() {}
    override fun changeDisplayGraph() {}
}