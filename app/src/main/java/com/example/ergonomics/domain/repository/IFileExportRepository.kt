package com.example.ergonomics.domain.repository

import com.example.ergonomics.domain.models.Measurement

interface IFileExportRepository {
    suspend fun exportMeasurements(
        measurement: List<Measurement>,
        fileName: String
    ): Result<Unit>
}