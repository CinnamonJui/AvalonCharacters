package com.example.avaloncharacters.characters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.avaloncharacters.R
import kotlinx.android.synthetic.main.character_view.view.*

class CharacterView : ConstraintLayout{

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        View.inflate(context, R.layout.character_view, this)
    }

    // change character color and number       # 1: evilside, 2: special, 3: unknown
    fun setCharacter(num: Int, type: Int) {

        character_circle.setText(num.toString())

        val grad: GradientDrawable = character_circle.background as GradientDrawable

        if(type == 1){
            grad.setColor(Color.parseColor("#EA0000"))
        } else if(type == 2){
            grad.setColor(Color.parseColor("#00A600"))
        } else{
            grad.setColor(Color.parseColor("#BEBEBE"))
        }

    }

    fun isChoosed(): Boolean {
        return character_ring.isChecked
    }

}