package com.example.ergonomics.di

import com.example.ergonomics.data.repository.RepositoryImpl
import com.example.ergonomics.domain.repository.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    //IRepository
    @Provides
    @Singleton
    fun provideRepository(): IRepository {
        return RepositoryImpl()
    }



}