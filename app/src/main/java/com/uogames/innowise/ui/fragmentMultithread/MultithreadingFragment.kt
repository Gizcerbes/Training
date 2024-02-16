package com.uogames.innowise.ui.fragmentMultithread

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MultithreadingFragment : Fragment() {

	private val vm by lazy { ViewModelProvider(requireActivity())[MultithreadingViewModel::class.java] }

	private var observers: Job? = null
	private var tv: TextView? = null
	private var layout: RelativeLayout? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		layout = RelativeLayout(inflater.context)
		tv = TextView(requireContext()).apply {
			val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
			lp.addRule(RelativeLayout.CENTER_IN_PARENT)
			layoutParams = lp
		}
		layout?.addView(tv)
		return layout
	}


	override fun onStart() {
		super.onStart()
		observers = lifecycleScope.launch {
			launch { vm.session.collect { tv?.text = it.toString() } }
			launch {
				var counter = 0
				vm.message.collect {
					val mes = if (counter++ % 4 == 3) "Surprise" else it
					Toast.makeText(requireContext(), mes, Toast.LENGTH_SHORT).show()
				}
			}
		}
	}

	override fun onStop() {
		super.onStop()
		observers?.cancel()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		tv = null
		layout = null
	}

}