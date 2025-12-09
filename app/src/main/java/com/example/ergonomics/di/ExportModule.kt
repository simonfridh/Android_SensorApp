package com.example.ergonomics.di

import android.content.Context
import com.example.ergonomics.data.export.FileExportRepositoryImpl
import com.example.ergonomics.domain.repository.IFileExportRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExportModule {

    @Provides
    @Singleton
    fun provideFileExportRepository(
        @ApplicationContext context: Context
    ): IFileExportRepository{
        return FileExportRepositoryImpl(context)
    }
}