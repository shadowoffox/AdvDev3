package com.example.advdev3

import android.app.Application
import com.example.advdev3.di.application
import com.example.advdev3.di.mainScreen
import org.koin.core.context.startKoin

class TranslatorApp:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { modules(listOf(application, mainScreen))}
    }
}