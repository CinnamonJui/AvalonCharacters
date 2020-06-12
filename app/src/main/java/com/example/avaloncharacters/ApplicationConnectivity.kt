package com.example.avaloncharacters

import android.app.Application

class ApplicationConnectivity : Application() {
    companion object {
        private val TAG = ApplicationConnectivity::class::simpleName.get()!!
    }
}