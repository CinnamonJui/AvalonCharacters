package com.example.avaloncharacters.connection

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import com.example.avaloncharacters.characters.Player
import com.google.android.gms.nearby.connection.*


class ServerSideConnection(context: Context) : Connection(context) {
    // EndpointId to Player
    val mConnectedDeviceMap = HashMap<String, Player?>(4)

    override val mConnectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionResult(
            endpointId: String,
            connectionResolution: ConnectionResolution
        ) {
            when (connectionResolution.status.statusCode) {
                ConnectionsStatusCodes.SUCCESS -> {
                    mConnectedDeviceMap[endpointId] = null
                }
                ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                    Log.i(
                        TAG,
                        connectionResolution.status.statusMessage ?: "STATUS_CONNECTION_REJECTED"
                    )
                }
                else -> Log.wtf(
                    TAG,
                    "onConnectionResult: ${connectionResolution.status.statusMessage}"
                )
            }
        }

        override fun onDisconnected(endpointId: String) {
            mConnectedDeviceMap.clear()
            mConnectionsClient.stopAllEndpoints()
            TODO("End Game")
        }

        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            if (mConnectedDeviceMap.containsKey(endpointId))
                mConnectionsClient.rejectConnection(endpointId)

            AlertDialog.Builder(context).apply {
                setCancelable(false)
                setTitle("Accept incoming connection from ${info.endpointName}, $endpointId")
                setMessage(
                    "For more careful user, here is Authentication Token:\n" +
                            info.authenticationToken
                )

                //Request connection
                setPositiveButton("Accept") { _, _ ->
                    mConnectionsClient.acceptConnection(
                        endpointId,
                        mPayloadCallback
                    )
                }
                setNegativeButton("Reject") { _, _ ->
                    mConnectionsClient.rejectConnection(endpointId)
                }
            }.show()
        }
    }
    override val mPayloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            TODO("not implemented, may be I should leave all hard works to Mrs. Hong?")
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            Log.i(
                TAG, "$endpointId: Transferring..." +
                        "\tprogress: ${update.bytesTransferred} / ${update.totalBytes}"
            )
        }
    }

    override fun startConnection(roomNumber: Long) {
        this.roomNumber = roomNumber

        Log.i(TAG, "Start advertising, room number: $roomNumber")
        mConnectionsClient.startAdvertising(
            player.name,
            packageName + roomNumber.toString(),
            mConnectionLifecycleCallback,
            advertisingOptions
        )
    }


    companion object {
        internal val TAG = ServerSideConnection::class::simpleName.get()!!
        private val advertisingOptions: AdvertisingOptions by lazy {
            AdvertisingOptions.Builder().apply {
                setStrategy(Strategy.P2P_STAR)
            }.build()
        }
    }
}