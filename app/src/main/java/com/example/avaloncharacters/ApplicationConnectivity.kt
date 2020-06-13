package com.example.avaloncharacters

import android.app.Application
import com.example.avaloncharacters.characters.Player
import com.example.avaloncharacters.connection.Connection

class ApplicationConnectivity : Application() {
    var connection: Connection? = null
    lateinit var player: Player

    companion object {
        private val TAG = ApplicationConnectivity::class::simpleName.get()!!
    }
}