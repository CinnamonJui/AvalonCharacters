package connection

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.connection.*


class ServerSideConnection(context: Context) : Connection(context) {
    override val mConnectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionResult(
            endpointId: String,
            connectionResolution: ConnectionResolution
        ) {
            TODO("not implemented")
        }

        override fun onDisconnected(endpointId: String) {
            TODO("End Game")
        }

        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            AlertDialog.Builder(context).apply {
                setCancelable(false)
                setTitle("Accept incoming connection from ${info.endpointName}, $endpointId")
                setMessage(
                    "For more careful user, here is Authentication Token:\n" +
                            info.authenticationToken
                )

                //Request connection
                setPositiveButton("Connect") { _, _ ->
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
            TODO("not implemented")
        }
    }

    fun startAdvertising(roomNumber: Long) {
        this.roomNumber = roomNumber

        Log.i(TAG, "Start advertising, room number: $roomNumber")
        mConnectionsClient.startAdvertising(
            player.name,
            packageName + roomNumber.toString(),
            mConnectionLifecycleCallback,
            advertisingOptions
        )
    }

    fun stopAdvertising() {
        Log.i(TAG, "Stop advertising")
        mConnectionsClient.stopAdvertising()
    }

    companion object {
        private val advertisingOptions = AdvertisingOptions.Builder().apply {
            setStrategy(Strategy.P2P_STAR)
        }.build()
    }
}