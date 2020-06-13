package com.example.avaloncharacters.title

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.avaloncharacters.ApplicationConnectivity
import com.example.avaloncharacters.R
import com.example.avaloncharacters.characters.Player
import com.example.avaloncharacters.connection.ClientSideConnection
import com.example.avaloncharacters.connection.Connection
import com.example.avaloncharacters.connection.ServerSideConnection
import kotlinx.android.synthetic.main.fragment_title.*
import kotlinx.android.synthetic.main.fragment_title.textRoomNumber


class Title : Fragment() {
    private lateinit var gameRuleDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_title, container, false)
    }


    private fun isValidInput(): Boolean {
        val playerName = textPlayerName.text.toString().takeIf {
            it.isNotEmpty()
        }

        val roomNumber = textRoomNumber.text.toString().takeIf {
            it.length == Connection.ROOMNUMBER_LENGTH
        }?.toLong()

        if (playerName == null) {
            Toast.makeText(context, "Player name can't be empty", Toast.LENGTH_SHORT).show()
            return false
        }

        if (roomNumber == null) {
            Toast.makeText(context, "6 digit required", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameRuleDialog = AlertDialog.Builder(context).apply {
            setTitle(getString(R.string.title_rule))
            setMessage(getString(R.string.rule_explanation))
            setCancelable(true)
            setPositiveButton("OK", null)
        }.create()

        btnRuleHelp.setOnClickListener {
            gameRuleDialog.show()
        }
        btnEnterRoom.setOnClickListener {
            if (!isValidInput())
                return@setOnClickListener

            val roomNumber = textRoomNumber.text.toString().toLong()
            val playerName = textPlayerName.text.toString()

            (requireActivity().application as ApplicationConnectivity).run {
                player = Player(playerName)
                connection = ClientSideConnection(requireContext()).also {
                    it.startConnection(roomNumber)
                }
            }

            AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_MinWidth)
                .apply {
                    setView(R.layout.wait_connection_dialog_fragment)
                    setOnDismissListener { _ ->
                        (requireActivity().application as ApplicationConnectivity)
                            .connection?.stopConnection()
                    }
                }.show()
        }

        btnNewRoom.setOnClickListener {
            if (!isValidInput())
                return@setOnClickListener

            val roomNumber = textRoomNumber.text.toString().toLong()
            val playerName = textPlayerName.text.toString()

            (requireActivity().application as ApplicationConnectivity).run {
                player = Player(playerName)
                connection = ServerSideConnection(requireContext()).also {
                    it.startConnection(roomNumber)
                }
            }

            val action =
                TitleDirections.actionGameTitleToWaitPlayerEnter(roomNumber)

            NavHostFragment.findNavController(this@Title)
                .navigate(action)
        }
    }
}
