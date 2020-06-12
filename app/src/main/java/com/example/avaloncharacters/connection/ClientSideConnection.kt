package com.example.avaloncharacters.connection

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.avaloncharacters.ApplicationConnectivity
import com.google.android.gms.nearby.connection.*

class ClientSideConnection(context: Context) : Connection(context) {
    override val mPayloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            TODO("not implemented, may be I should leave all hard works to Mrs. Hong?")
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            TODO("not implemented")
        }
    }
    override val mConnectionLifecycleCallback = object : ConnectionLifecycleCallback() {

        override fun onConnectionResult(
            endpointId: String,
            connectionResolution: ConnectionResolution
        ) {
            when (connectionResolution.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    Toast.makeText(
                        context,
                        "Successfully connected!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ConnectionsStatusCodes.STATUS_ERROR -> {

                }
                ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                    Toast.makeText(
                        context,
                        "Connection rejected!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Log.wtf(
                        ApplicationConnectivity.TAG,
                        "onConnectionResult: ${connectionResolution.status.statusMessage}"
                    )
                }
            }
        }

        override fun onDisconnected(endpointId: String) {
            TODO("onDisconnected, end game")
        }

        override fun onConnectionInitiated(
            endpointId: String,
            connectionInfo: ConnectionInfo
        ) {
            mConnectionsClient.acceptConnection(endpointId, mPayloadCallback)
            Log.i(TAG, "Connected with $endpointId")
        }
    }

    private val mEndpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        // Find server
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            AlertDialog.Builder(context).apply {
                setCancelable(false)
                setTitle("Accept connection to ${info.endpointName}, $endpointId")
                setMessage("Service ID: ${info.serviceId}")

                //Request connection
                setPositiveButton("Connect!") { _, _ ->
                    mConnectionsClient.requestConnection(
                        player.name,
                        endpointId,
                        mConnectionLifecycleCallback
                    )
                }
                setNegativeButton("Cancel", null)
            }.show()
        }

        override fun onEndpointLost(endpointId: String) = TODO("End Game")
    }

    fun startDiscovery(roomNumber: Long) {
        this.roomNumber = roomNumber
        val discoveryOptions = DiscoveryOptions.Builder().apply {
            setStrategy(Strategy.P2P_STAR)
        }.build()

        Log.i(TAG, "Start discovering, room number: $roomNumber")
        mConnectionsClient.startDiscovery(
            packageName + roomNumber,
            mEndpointDiscoveryCallback,
            discoveryOptions
        )
    }

    fun stopDiscovering() {
        Log.i(TAG, "Stop discovering")
        mConnectionsClient.stopDiscovery()
    }
}
