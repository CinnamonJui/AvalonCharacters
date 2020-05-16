package com.example.avaloncharacters

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.example.avaloncharacters.characters.Player
import com.example.avaloncharacters.characters.goodside.GoodSide
import kotlinx.android.synthetic.main.choice_count_down_view.view.*


class ChoiceCountDownView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val GAMEPHASES: Array<String> = context.resources.getStringArray(R.array.game_phases)
    private lateinit var player: Player
    fun setPlayer(p: Player) {
        player = p
    }

    private var choiceType: Int = 0

    init {
        View.inflate(context, R.layout.choice_count_down_view, this)
        btnPositive.setOnClickListener {
            confirm.isEnabled = true
        }
        btnNegative.setOnClickListener {
            confirm.isEnabled = true
        }
        this.visibility = View.INVISIBLE
    }


    fun startCountDown(type: Int, seconds: Int, callback: (() -> Unit)? = null) {
        choiceType = type

        confirm.isEnabled = false
        btnNegative.visibility = View.VISIBLE
        val goodsideEnterMission = choiceType == MISSION && player.character is GoodSide
        if (goodsideEnterMission) {
            confirm.isEnabled = true
            btnNegative.visibility = View.GONE
            btnPositive.isChecked = true
        }
        countDownProgressBar.progress = 100
        countDownSeconds.text = seconds.toString()
        titleText.text = GAMEPHASES[type]

        visibility = View.VISIBLE
        (object : CountDownTimer(seconds * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsUntilFinished = (millisUntilFinished / 1000).toInt()

                countDownProgressBar.setProgress(100 * secondsUntilFinished / seconds, true)
                countDownSeconds.text = secondsUntilFinished.toString()
            }

            override fun onFinish() {
                visibility = View.INVISIBLE
                callback?.invoke()
                Log.v(TAG, Thread.currentThread().name)
            }
        }).start()
    }


    companion object {
        const val TAG = "ChoiceCountDownView"
        const val TEAMBUILD = 0
        const val VOTETEAM = 1
        const val MISSION = 2
    }
}
