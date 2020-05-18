package com.example.avaloncharacters.characters

import android.content.Context
import android.graphics.Bitmap
import com.example.avaloncharacters.characters.evilside.*
import com.example.avaloncharacters.characters.goodside.LoyalServantOfArthur
import com.example.avaloncharacters.characters.goodside.Merlin
import com.example.avaloncharacters.characters.goodside.Percival
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor

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

    companion object {
        private val constructors = HashMap<String, KFunction<Character>>().apply {
            put("Merlin", Merlin::class.primaryConstructor!!)
            put("Percival", Percival::class.primaryConstructor!!)
            put("Loyal Servant of Arthur", LoyalServantOfArthur::class.primaryConstructor!!)
            put("Mordred", Mordred::class.primaryConstructor!!)
            put("Morgana", Morgana::class.primaryConstructor!!)
            put("Assassin", Assassin::class.primaryConstructor!!)
            put("Oberon", Oberon::class.primaryConstructor!!)
            put("Minion of Mordred", MinionOfMordred::class.primaryConstructor!!)
        }
        val ValidCharacterName = setOf(
            "Merlin",
            "Percival",
            "Loyal Servant of Arthur",
            "Mordred",
            "Morgana",
            "Assassin",
            "Oberon",
            "Minion of Mordred"
        )

        fun newInstance(characterName: String, context: Context): Character {
            if (!ValidCharacterName.contains(characterName))
                throw IllegalArgumentException("No such a character name: $characterName")

            return constructors[characterName]!!.call(context)
        }

    }
}


