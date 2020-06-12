package com.example.avaloncharacters.connection

import android.content.Context
import com.example.avaloncharacters.ApplicationConnectivity
import com.example.avaloncharacters.characters.Player
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.PayloadCallback

abstract class Connection(context: Context) {
    public lateinit var player: Player
    protected val mConnectionsClient = Nearby.getConnectionsClient(context)!!
    protected val packageName: String = context.packageName
    protected var roomNumber: Long = -1
        set(value) {
            if (value.toString().length != ApplicationConnectivity.ROOMNUMBER_LENGTH)
                throw IllegalArgumentException(
                    "Length of room number must be equal to ${ApplicationConnectivity.ROOMNUMBER_LENGTH}"
                )
            field = value
        }

    abstract val mConnectionLifecycleCallback: ConnectionLifecycleCallback
    abstract val mPayloadCallback: PayloadCallback

    companion object {
        const val TAG = "Connection"
        const val ROOMNUMBER_LENGTH = 6
    }
}
