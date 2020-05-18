package com.example.avaloncharacters.characters.evilside

import android.content.Context
import android.graphics.Bitmap
import com.example.avaloncharacters.R
import com.example.avaloncharacters.characters.Character

open class EvilSide(context: Context) : Character(context) {
    override val characterName: String by lazy {
        context.getString(R.string.avalon_Minion_of_Mordred)
    }
    override val characterSelfIntro: String = ""
    override val characterImage: Bitmap
        get() = TODO("not implemented")
}

class MinionOfMordred(context: Context) : EvilSide(context) {

}

class Assassin(context: Context) : EvilSide(context) {
    override val characterName: String by lazy {
        context.getString(R.string.avalon_Assassin)
    }
    override val characterSelfIntro: String by lazy {
        context.getString(R.string.avalon_Minion_of_Mordred)
    }
}

class Mordred(context: Context) : EvilSide(context) {
    override val characterName: String by lazy {
        context.getString(R.string.avalon_Mordred)
    }
    override val characterSelfIntro: String by lazy {
        context.getString(R.string.avalon_Mordred_self_intro)
    }
}

class Morgana(context: Context) : EvilSide(context) {
    override val characterName: String by lazy {
        context.getString(R.string.avalon_Morgana)
    }
    override val characterSelfIntro: String by lazy {
        context.getString(R.string.avalon_Morgana_self_intro)
    }
}

class Oberon(context: Context) : EvilSide(context) {
    override val characterName: String by lazy {
        context.getString(R.string.avalon_Oberon)
    }
    override val characterSelfIntro: String by lazy {
        context.getString(R.string.avalon_Oberon_self_intro)
    }
}
