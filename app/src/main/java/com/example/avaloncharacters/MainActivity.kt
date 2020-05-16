package com.example.avaloncharacters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.avaloncharacters.helpers.PermissionHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            add(PermissionHelper(), "Permission")
            commitNow()
            // This fragment will be auto destroyed
        }
    }
}
