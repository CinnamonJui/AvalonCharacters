package com.example.avaloncharacters.characters.goodside

import android.content.Context
import android.graphics.Bitmap
import com.example.avaloncharacters.R
import com.example.avaloncharacters.characters.Character


open class GoodSide(context: Context) : Character(context) {
    override val characterName: String by lazy {
        context.getString(R.string.avalon_Loyal_servant_of_Arthur)
    }
    override val characterSelfIntro: String = ""
    override val characterImage: Bitmap
        get() = TODO("not implemented")
}

class LoyalServantOfArthur(context: Context) : GoodSide(context) {

}

class Merlin(context: Context) : GoodSide(context) {
    override val characterName: String by lazy {
        context.getString(R.string.avalon_Merlin)
    }
    override val characterSelfIntro: String by lazy {
        context.getString(R.string.avalon_Merlin_self_intro)
    }
}

class Percival(context: Context) : GoodSide(context) {
    override val characterName: String by lazy {
        context.getString(R.string.avalon_Percival)
    }
    override val characterSelfIntro: String by lazy {
        context.getString(R.string.avalon_Percival_self_intro)
    }
}