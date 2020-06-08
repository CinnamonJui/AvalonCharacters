package com.example.avaloncharacters.helpers

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.avaloncharacters.R
import kotlinx.android.synthetic.main.mission_record.view.*

class MissionRecord : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        View.inflate(context, R.layout.mission_record, this)
        mission_result.text = "" //pass or not pass
        mission_result.setBackgroundColor(getResources().getColor(R.color.background_default))
    }

    /**
     * set mission title
     * @param missionIndex The index of mission(will go through at most 5 mission)
     */
    fun setMissionTitle(missionIndex: Int) {
        mission_title.text = context.getString(R.string.mission_title, missionIndex)
    }

    /**
     * set mission table data
     * @param total_round The rounds of voting in single mission
     * @param vote_record Vote record of each round and each player
     * @param king Who assign the mission
     * @param assignee Who being assigned the mission by king
     */
//    , assignee: Array<Int>
    fun setMissionTable(total_round: Int, vote_record: Array<Array<Int>>, king:Array<Int>, assignee:Array<Array<Int>>) {
        for (round in 0 until total_round) {
            // set new row which represent a round
            val newRow = TableRow(context)
            newRow.id = round
            newRow.layoutParams = TABLE_ROW_LAYOUT_PARAM
            // set round name according to round index(round index = 1,2...)
            val roundTxt = TextView(context)
            val roundIndex:Int = round+1
            roundTxt.text = context.getString(R.string.round_name, roundIndex)
            roundTxt.textAlignment = View.TEXT_ALIGNMENT_CENTER
            newRow.addView(roundTxt)
            // set each player's vote data each round
            for ((index, vote) in vote_record[round].withIndex()) {
                val newData = TextView(context)
                newData.text = VOTE_SIGN[vote]
                newData.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                //set king icon
                if((index+1) == king[round]) {
                    newData.compoundDrawablePadding = 0
                    newData.setCompoundDrawablesWithIntrinsicBounds(R.drawable.crown, 0, 0, 0);
                }
                //set assignee
                if(assignee[round].contains(index+1)) {
                    newData.setBackgroundColor(getResources().getColor(R.color.assignee))
                }
                newRow.addView(newData)
            }
            // add new row to table
            mission_table.addView(newRow)
        }
    }

    /**
     * set Mission Result
     * @param state The state of mission(success/fail/voting)
     */
    fun setMissionResult(state: Int) {
        if (state == SUCCESS) {
            mission_result.text = "通過"
            mission_result.setBackgroundColor(getResources().getColor(R.color.background_success))
        } else if (state == FAIL) {
            mission_result.text = "未通過"
            mission_result.setBackgroundColor(getResources().getColor(R.color.background_failed))
        } else if (state == VOTING) {
            mission_result.text = "投票中"
            mission_result.setBackgroundColor(getResources().getColor(R.color.background_default))
        }
    }

    companion object {
        const val SUCCESS = 1
        const val FAIL = -1
        const val VOTING = 0

        val TABLE_ROW_LAYOUT_PARAM = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        val VOTE_SIGN = arrayOf("X", "O")
    }
}