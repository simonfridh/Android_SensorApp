package com.example.ergonomics.data.repository

import android.util.Log
import com.example.ergonomics.domain.repository.IRepository

class RepositoryImpl(

): IRepository {

    override fun test() {
        Log.d("ErgonomicsApp", "HELLO WORLD")
    }

}