package com.example.avaloncharacters.characters

import android.content.Context
import android.graphics.Bitmap
import com.example.avaloncharacters.R

abstract class Character(context: Context) {
    /**
     * Example: Merlin
     */
    abstract val characterName: String
    /**
     * Able to know evil side
     */
    abstract val characterSelfIntro: String
    abstract val characterImage: Bitmap
}


