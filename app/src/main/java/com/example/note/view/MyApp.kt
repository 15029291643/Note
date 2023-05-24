package com.example.note.view

import android.app.Application
import android.content.Context

class MyApp : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}