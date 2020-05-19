package com.example.avaloncharacters

import android.app.AlertDialog
import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.avaloncharacters.characters.Player
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*

class ApplicationConnectivity : Application() {
    private lateinit var mConnectionsClient: ConnectionsClient
    private val mEndpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
            override fun onConnectionResult(
                endpointId: String,
                connectionResolution: ConnectionResolution
            ) {
                when (connectionResolution.status.statusCode) {
                    ConnectionsStatusCodes.STATUS_OK -> {
                        Toast.makeText(
                            this@ApplicationConnectivity,
                            "Successfully connected!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    ConnectionsStatusCodes.STATUS_ERROR -> {

                    }
                    ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                        Toast.makeText(
                            this@ApplicationConnectivity,
                            "Connection rejected!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Log.wtf(
                            TAG,
                            "onConnectionResult: ${connectionResolution.status.statusMessage}"
                        )
                    }
                }
            }

            override fun onDisconnected(endpointId: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            AlertDialog.Builder(this@ApplicationConnectivity).apply {
                setCancelable(true)
                setTitle("Accept connection to ${info.endpointName}, $endpointId")
                setMessage("Service ID" + info.serviceId)
                setPositiveButton("Connect!") { _, _ ->
                    mConnectionsClient.requestConnection(
                        player.name,
                        endpointId,
                        connectionLifecycleCallback
                    )
                }
                setNegativeButton("Cancel", null)
            }
        }

        override fun onEndpointLost(endpointId: String) = TODO("End Game")
    }
    private val mConnectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDisconnected(endpointId: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private lateinit var player: Player
    fun setPlayer(p: Player) {
        player = p
    }

    private var roomNumber: Long = 123456
        set(value) {
            if (value.toString().length != ROOMNUMBER_LENGTH)
                throw IllegalArgumentException("Length of room number must equal to $ROOMNUMBER_LENGTH")
            field = value
        }

    fun startDiscovery(roomNumber: Long) {
        this.roomNumber = roomNumber
        val discoveryOptions = DiscoveryOptions.Builder().apply {
            setStrategy(Strategy.P2P_STAR)
        }.build()

        Log.i(TAG, "startDiscovery")
        mConnectionsClient.startDiscovery(
            packageName + roomNumber,
            mEndpointDiscoveryCallback,
            discoveryOptions
        )
    }

    fun stopDiscovering() = mConnectionsClient.stopDiscovery()

    fun startAdvertising(roomNumber: Long) {
        this.roomNumber = roomNumber
        val advertisingOptions = AdvertisingOptions.Builder().apply {
            setStrategy(Strategy.P2P_STAR)
        }.build()

        Log.i(TAG, "Start Advertising, Room Number: $roomNumber")
        mConnectionsClient.startAdvertising(
            player.name,
            packageName + roomNumber.toString(),
            mConnectionLifecycleCallback,
            advertisingOptions
        )
    }

    fun stopAdvertising() {
        Log.d(TAG, "Stop Advertising")
        mConnectionsClient.stopAdvertising()
    }


    override fun onCreate() {
        super.onCreate()
        mConnectionsClient = Nearby.getConnectionsClient(this)
    }


    companion object {
        const val TAG = "ApplicationConnectivity"
        const val ROOMNUMBER_LENGTH = 6
    }
}