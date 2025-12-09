package com.example.ergonomics.data.export

import android.content.Context
import android.os.Environment
import com.example.ergonomics.domain.models.Measurement
import com.example.ergonomics.domain.repository.IFileExportRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okio.IOException
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileExportRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): IFileExportRepository{

    override suspend fun exportMeasurements(
        measurement: List<Measurement>,
        fileName: String
    ): Result<Unit> {
        val name = if(fileName.endsWith(".csv")) fileName else "$fileName.csv"
        val downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if(!downloads.exists()) downloads.mkdirs()

        val file = File(downloads, name)

        try{
            FileOutputStream(file).use { fos ->
                val writer = fos.bufferedWriter()
                writer.write("timestamp, angle")
                writer.newLine()

                for(m in measurement){
                    writer.write("${m.timestamp}, ${m.angle}")
                    writer.newLine()
                }
                writer.flush()
            }
            return Result.success(Unit)
        }
        catch(e: IOException){
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}