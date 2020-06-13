package com.example.avaloncharacters.title

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WaitPlayerEnterViewModel(roomNumber: Long) : ViewModel() {
    val roomNumber = MutableLiveData(roomNumber)
    val playerNumber = MutableLiveData(1)
}
