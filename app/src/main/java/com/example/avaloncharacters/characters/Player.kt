package com.example.avaloncharacters.characters

import android.graphics.Bitmap


class Player(
    private val playerName: String,
    private val profileBitmap: Bitmap? = null
) {
    lateinit var character: Character

    fun assignCharacter(character: Character) {
        this.character = character
    }

    /**
     * Leader this round choose members in Team building phase
     * @param numOfTotalMember how many member should be picked this round
     */
    fun buildTeam(candidates: Iterable<Player>, numOfTotalMember: Int): Iterable<Player> {
        return listOf(this)
    }

    /**
     * Either approve or reject the team just built
     * @param leader Who chose these members
     * @param teamMembers Members chosen by leader
     * @param lastChance Will good side loose this round if the team is rejected?
     */
    fun isPlayerApproveTeamForQuest(
        leader: Player,
        teamMembers: Iterable<Character>,
        lastChance: Boolean
    ): Boolean {
        return true
    }

    /**
     * check if chosen member let quest success or fail (Evil only)
     */
    fun isPlayerLetQuestSuccess(
        leader: Player,
        teamMembers: Iterable<Player>
    ): Boolean {
        return true
    }
}