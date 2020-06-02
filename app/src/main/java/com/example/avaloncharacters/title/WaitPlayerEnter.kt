package com.example.avaloncharacters.title

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.avaloncharacters.ApplicationConnectivity
import com.example.avaloncharacters.R
import kotlinx.android.synthetic.main.wait_player_enter_fragment.*
import java.lang.ref.WeakReference
import kotlin.math.ceil


class WaitPlayerEnter : Fragment() {

    private lateinit var viewModel: WaitPlayerEnterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().apply {
            (application as ApplicationConnectivity)
            onBackPressedDispatcher.addCallback(this) {
                (requireActivity().application as ApplicationConnectivity).stopAdvertising()
                NavHostFragment.findNavController(this@WaitPlayerEnter).popBackStack()
                // WTF, Why do I have to manually call this bullshit again?
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wait_player_enter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val roomNumber = kotlin.run {
            val args: WaitPlayerEnterArgs by navArgs()
            args.roomNumber
        }
        (requireActivity().application as ApplicationConnectivity).startAdvertising(roomNumber)

        textRoomNumber.text = String.format(getString(R.string.room_number), roomNumber)
        btnCancelRoom.setOnClickListener {
            (requireActivity().application as ApplicationConnectivity).stopAdvertising()
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        loadingTextView.startAnimation()
    }

    override fun onPause() {
        super.onPause()
        loadingTextView.stopAnimation()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WaitPlayerEnterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

class LoadingTextView(context: Context, attrs: AttributeSet) :
    AppCompatTextView(context, attrs), Runnable {
    private val dotsArray = arrayOf(".", "..", "...")
    private var currentDotIdx = -1

    private val loadingText = context.getString(R.string.waiting_player)
    private fun next(): String {
        currentDotIdx = (currentDotIdx + 1) % dotsArray.size
        return dotsArray[currentDotIdx]
    }

    private val mHandler = WeakReference(Handler(context.mainLooper))

    private var isAnimating = true

    init {
        width = ceil(paint.measureText(loadingText + dotsArray.last())).toInt()
    }

    fun startAnimation() {
        currentDotIdx = -1
        isAnimating = true
        mHandler.get()?.postDelayed(this, 1000)
    }

    fun stopAnimation() {
        currentDotIdx = -1
        isAnimating = false
    }

    override fun run() {
        text = loadingText + next()
        if (isAnimating)
            mHandler.get()?.postDelayed(this, 1000)
    }
}