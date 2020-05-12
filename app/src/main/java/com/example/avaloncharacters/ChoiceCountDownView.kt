package com.example.avaloncharacters

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.example.avaloncharacters.characters.Player
import com.example.avaloncharacters.characters.goodside.GoodSide
import kotlinx.android.synthetic.main.choice_count_down_view.view.*


class ChoiceCountDownView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val GAME_PHASES: Array<String> = context.resources.getStringArray(R.array.game_phases)
    private lateinit var player: Player
    private var choiceType: Int = 0
    fun setPlayer(p: Player) {
        player = p
    }

    init {
        View.inflate(context, R.layout.choice_count_down_view, this).post {
            btnPositive.setOnClickListener {
                it.setBackgroundResource(R.color.btn_positive)
                btnNegative.setBackgroundResource(R.color.btn_neutral)
                confirm.isEnabled = true
            }
            btnNegative.setOnClickListener {
                if (choiceType == MISSION && player.character is GoodSide){
                    Toast.makeText(context, "Only evil side can let mission fail!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                it.setBackgroundResource(R.color.btn_negtive)
                btnPositive.setBackgroundResource(R.color.btn_neutral)
                confirm.isEnabled = true
            }

            this.visibility = View.INVISIBLE
        }
    }


    fun startCountDown(type: Int, seconds: Int, callback: (() -> Unit)? = null) {
        choiceType = type

        countDownProgressBar.progress = 100
        countDownSeconds.text = seconds.toString()
        titleText.text = GAME_PHASES[type]
        confirm.isEnabled = false

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
