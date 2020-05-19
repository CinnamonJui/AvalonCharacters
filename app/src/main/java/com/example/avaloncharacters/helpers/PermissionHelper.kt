package com.example.avaloncharacters.helpers

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

private const val PERMISSIONS_REQUEST_CODE = 10
private val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.BLUETOOTH,
    Manifest.permission.BLUETOOTH_ADMIN,
    Manifest.permission.ACCESS_WIFI_STATE,
    Manifest.permission.CHANGE_WIFI_STATE,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

class PermissionHelper : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(javaClass.name, "onCreate()")
        retainInstance = true

        val allPermissionsGranted = PERMISSIONS_REQUIRED.all { permission ->
            requireContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
        if (!allPermissionsGranted)
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
        else
            NavHostFragment.findNavController(this)
                .navigate(PermissionHelperDirections.actionPermissionHelperToGameTitle())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != PERMISSIONS_REQUEST_CODE) {
            Log.wtf("WTF", "Why do we have this request code?")
            return
        }

        val hasDenial = grantResults.any { result ->
            result == PackageManager.PERMISSION_DENIED
        }
        if (hasDenial) {
            Toast.makeText(
                requireContext(),
                "We need these permissions for connecting to nearby devices!",
                Toast.LENGTH_LONG
            ).show()
            requireActivity().finish()
        }
        NavHostFragment.findNavController(this)
            .navigate(PermissionHelperDirections.actionPermissionHelperToGameTitle())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(javaClass.name, "onDestroy()")
    }
}
