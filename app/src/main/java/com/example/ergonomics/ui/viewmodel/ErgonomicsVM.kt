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
    fun exportData(filename: String)
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

    private var filteredAccelerometerAngle = 0f
    private var fusionAngle = 0f
    private val _measurementTime = 30
    private var job: Job? = null  // coroutine for the measurement. Can be canceled
    private var filteredValuesList = mutableListOf<Measurement>() // Used to export algo1 values


    override fun startMeasurement() {
        stopMeasurement()
        _measurementState.value = _measurementState.value.copy(measurementSummary = emptyList())
        filteredValuesList = mutableListOf()

        // Set up sensors before measurement
        if (sensorRepository.sensorsAvailable()) {
            sensorRepository.startSensors()
            sensorRepository.setSensorsOnChange (
                accelerometerOnChange = { x, y, z ->
                    // Algorithm 1 - Linear Acceleration
                    filteredAccelerometerAngle = noiseFilter(
                        alpha = 0.7f,
                        currentAngle = calculateAccelerometerAngle(SensorValue(x,y,z)),
                        previousFilteredAngle = filteredAccelerometerAngle)
                },
                gyroscopeOnChange = { x, y, z, dt ->
                    // Algorithm 2 - Sensor Fusion
                    fusionAngle = sensorFusion(
                        alpha = 0.1f,
                        accelerometerAngle = filteredAccelerometerAngle,
                        calculateGyroscopeAngle(SensorValue(x,y,z),fusionAngle, dt)
                    )
                }
            )
        }

        //Start timed measurement
        job = viewModelScope.launch {
            _measurementState.value = _measurementState.value.copy(measurementRunning = true)
            delay(100) // small delay to give sensors some time to start
            fusionAngle = filteredAccelerometerAngle // start fusionValue at current accelerometerValue to speed up start
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
        // one loop is 0.05sec.
        // sensors are a bit faster but we read their values at a set speed
        for(i in 0 until _measurementTime * 20) {
            delay(50)
            val currentTime = (i+1).toFloat() / 20f //Current time in seconds

            // Read values from sensors and save them
            _measurementState.value = _measurementState.value.copy(
                totalTime = currentTime,
                currentAngle = fusionAngle,
                measurementSummary = _measurementState.value.measurementSummary + Measurement(fusionAngle, currentTime)
            )

            // Here we save the algorithm 1 values so we can show them in excel :D
            filteredValuesList.add(Measurement(filteredAccelerometerAngle,currentTime))
        }
    }

    //Shared between multiple components to show/hide the graph in the UI
    override fun changeDisplayGraph() {
        _measurementState.value = _measurementState.value.copy(displayGraph = !measurementState.value.displayGraph)
    }

    override fun exportData(filename: String) {
        // Two files are created.
        // Algorithm 2 values are stored in filename.csv, and Algorithm 1 values are stored in filename_algorithm1.csv
        viewModelScope.launch{
            fileExportRepository.exportMeasurements(measurementState.value.measurementSummary, filename)
            fileExportRepository.exportMeasurements(filteredValuesList, filename + "_algorithm1")
        }
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
    override fun exportData(filename: String) {}
}