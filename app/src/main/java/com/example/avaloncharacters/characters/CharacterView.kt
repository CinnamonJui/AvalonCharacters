package com.example.avaloncharacters.characters

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.avaloncharacters.R
import kotlinx.android.synthetic.main.character_view.view.*

class CharacterView : ConstraintLayout{

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        View.inflate(context, R.layout.character_view, this)
    }

    // change character color and number
    fun setCharacterIdentity(num: Int, type: Int) {

        character_circle.setText(num.toString())

        val grad: GradientDrawable = character_circle.background as GradientDrawable
        if(type == evilside){
            grad.setColor(ContextCompat.getColor(context, R.color.circle_evil))
        } else if(type == special){
            grad.setColor(ContextCompat.getColor(context, R.color.circle_special))
        } else{
            grad.setColor(ContextCompat.getColor(context, R.color.circle_unknown))
        }

    }

    fun isChoosed(): Boolean {
        return character_ring.isChecked
    }

    companion object {
        const val evilside = 1
        const val special = 2
        const val unknown = 3
    }

}
