package com.example.avaloncharacters.helpers

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.avaloncharacters.R
import com.example.avaloncharacters.characters.Player
import kotlinx.android.synthetic.main.mission_record.view.*

class MissionRecord:RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private lateinit var player: Player

    init {
        View.inflate(context, R.layout.mission_record, this)
        mission_title.text="" // mission 1,2,3...
        mission_detail.text="" //details of mission: the player who assign mission and assigned player
        mission_result.text="" //pass or not pass
        mission_result.setBackgroundColor(getResources().getColor(R.color.backgroud_default))
        player1.text = ""
        player2.text = ""
        player3.text = ""
        player4.text = ""
        player5.text = ""
        this.visibility = View.INVISIBLE
    }

    fun setMissionTitle(missionIndex: Int)
    {
        var title:String="任務"
        mission_title.text = title+ missionIndex.toString()
    }

    fun setMissionDetail(mission_player:Array<String>)
    {
        //exp: mission_player=[1,3,5]
        var detailMessage:String=""
        detailMessage += mission_player[0]+" 派 "

        for (player in 1 until mission_player.size){
            detailMessage+=mission_player[player]+"  "
        }
        detailMessage +="出任務"

    }

    fun setVoteResult(vote:Array<String>)
    {
        //exp: vote=[0,1,1,0,0], index=0 represents player 1
        //0 means player disagree mission, 1 means player agree the mission

        //Following are the setVoteResult function work:
        //1. update the O/X in the table
        //2. show mission is success or not
        //3. change the background color(background_success/background_failed)
    }
}

