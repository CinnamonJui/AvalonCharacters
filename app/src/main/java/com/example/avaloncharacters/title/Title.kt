package com.example.avaloncharacters.title

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.avaloncharacters.R
import kotlinx.android.synthetic.main.fragment_title.*


class Title : Fragment() {
    private lateinit var gameRuleDialog: AlertDialog
    private lateinit var enterRoomDialog: AlertDialog
    private lateinit var newRoomDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        gameRuleDialog = AlertDialog.Builder(context).apply {
            setTitle(getString(R.string.title_rule))
            setMessage(getString(R.string.rule_explanation))
            setCancelable(true)
            setPositiveButton("OK", null)
        }.create()

        val createDialogContentView = fun(): Pair<View, EditText> {
            val roomNumberEditTextView = EditText(context).apply {
                maxLines = 1
                filters = arrayOf(InputFilter.LengthFilter(6))
                inputType = InputType.TYPE_CLASS_NUMBER
                hint = "6 digits required"
                layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ).apply {
                    val margin = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        24f,
                        resources.displayMetrics
                    ).toInt()
                    setMargins(margin, margin / 2, margin, margin / 2)
                }
            }
            val parent = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
            }
            parent.addView(roomNumberEditTextView)
            return (parent as View) to roomNumberEditTextView
        }
        enterRoomDialog = AlertDialog.Builder(context).apply {
            val (view, editText) = createDialogContentView()

            setTitle(getString(R.string.enter_room_number))
            setView(view)
            setPositiveButton("Enter Room") { dialog, _ ->
                if (editText.text.length != 6) {
                    dialog.dismiss()
                    Toast.makeText(context, "6 digit required", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                TODO("Start listening this room number")
            }
            setCancelable(true)
        }.create()
        newRoomDialog = AlertDialog.Builder(context).apply {
            val (view, editText) = createDialogContentView()

            setTitle(getString(R.string.new_room_number))
            setView(view)
            setPositiveButton(getString(R.string.create_room)) { dialog, _ ->
                if (editText.text.length != 6) {
                    dialog.dismiss()
                    Toast.makeText(context, "6 digit required", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                TODO("Start advertising this room number")
            }
            setCancelable(true)
        }.create()

        return inflater.inflate(R.layout.fragment_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment
        btnRuleHelp.setOnClickListener {
            gameRuleDialog.show()
        }
        btnEnterRoom.setOnClickListener {
            enterRoomDialog.show()
        }
        btnNewRoom.setOnClickListener {
            newRoomDialog.show()
        }
    }
}
