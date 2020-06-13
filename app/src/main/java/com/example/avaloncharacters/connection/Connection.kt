package com.example.avaloncharacters.connection

import android.content.Context
import android.util.Log
import com.example.avaloncharacters.ApplicationConnectivity
import com.example.avaloncharacters.characters.Player
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import java.nio.charset.StandardCharsets

abstract class Connection(context: Context) {
    public val player: Player = (context.applicationContext as ApplicationConnectivity).player
    protected val mConnectionsClient = Nearby.getConnectionsClient(context)!!
    protected val packageName: String = context.packageName
    protected var roomNumber: Long = -1
        set(value) {
            if (value.toString().length != ROOMNUMBER_LENGTH)
                throw IllegalArgumentException(
                    "Length of room number must be equal to $ROOMNUMBER_LENGTH"
                )
            field = value
        }

    abstract val mConnectionLifecycleCallback: ConnectionLifecycleCallback
    abstract val mPayloadCallback: PayloadCallback

    fun sendJSON(endpointId: String, jsonString: String): Unit {
        sendJSON(listOf(endpointId), jsonString)
    }

    fun sendJSON(endpointIds: List<String>, jsonString: String): Unit {
        Payload.fromStream(jsonString.byteInputStream(StandardCharsets.UTF_8))
            .also { payload ->
                mConnectionsClient.sendPayload(endpointIds, payload)
            }
    }

    abstract fun startConnection(roomNumber: Long)
    fun stopConnection() {
        if (this is ServerSideConnection) {
            Log.i(ServerSideConnection.TAG, "Stop advertising")
            mConnectionsClient.stopAdvertising()
        } else if (this is ClientSideConnection) {
            Log.i(ClientSideConnection.TAG, "Stop discovering")
            mConnectionsClient.stopDiscovery()
        } else
            throw ClassNotFoundException("Unknown Connection type")
    }

    val isServer by lazy {
        this is ServerSideConnection
    }

    companion object {
        const val TAG = "Connection"
        const val ROOMNUMBER_LENGTH = 6
    }
}
