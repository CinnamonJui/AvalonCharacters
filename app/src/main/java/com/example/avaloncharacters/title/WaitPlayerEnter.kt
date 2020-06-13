package com.example.avaloncharacters.title

import android.annotation.SuppressLint
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
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.avaloncharacters.ApplicationConnectivity
import com.example.avaloncharacters.R
import kotlinx.android.synthetic.main.wait_player_enter_fragment.*
import java.lang.ref.WeakReference
import kotlin.math.ceil


class WaitPlayerEnter : Fragment() {
    private lateinit var viewModel: WaitPlayerEnterViewModel
    private var roomNumber: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().apply {
            onBackPressedDispatcher.addCallback(this) {
                (requireActivity().application as ApplicationConnectivity).connection?.stopConnection()
                NavHostFragment.findNavController(this@WaitPlayerEnter).popBackStack()
                // Why do I have to manually call this method again?
            }
        }
        roomNumber = kotlin.run {
            val args: WaitPlayerEnterArgs by navArgs()
            args.roomNumber
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

        lifecycle.addObserver(loadingTextView)

        val connection =
            (requireActivity().application as ApplicationConnectivity).connection
                ?: throw NullPointerException("connection isn't initialized")

        btnCancelRoom.setOnClickListener {
            connection.stopConnection()
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        class ViewModelFactory(private val roomNumber: Long) : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(Long::class.java)
                    .newInstance(roomNumber)
            }
        }

        viewModel = ViewModelProvider(this, ViewModelFactory(roomNumber))
            .get(WaitPlayerEnterViewModel::class.java)
            .also { vm ->
                val lifecycleOwner = requireActivity()

                Observer<Long> { roomNumber ->
                    textRoomNumber.text = getString(R.string.room_number, roomNumber)
                }.run {
                    vm.roomNumber.observe(lifecycleOwner, this)
                }
                Observer<Int> { playerNumber ->
                    textPlayNumber.text = getString(R.string.play_number, playerNumber)
                }.run {
                    vm.playerNumber.observe(lifecycleOwner, this)
                }
            }
    }

}

private class LoadingTextView(context: Context, attrs: AttributeSet) :
    AppCompatTextView(context, attrs), Runnable, LifecycleObserver {
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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startAnimation() {
        currentDotIdx = -1
        isAnimating = true
        mHandler.get()?.postDelayed(this, 1000)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopAnimation() {
        currentDotIdx = -1
        isAnimating = false
    }

    @SuppressLint("SetTextI18n")
    override fun run() {
        text = loadingText + next()
        if (isAnimating)
            mHandler.get()?.postDelayed(this, 1000)
    }
}