package com.uogames.innowise.ui.fragmentCounter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uogames.innowise.R
import com.uogames.innowise.databinding.FragmentCounterBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CounterFragment : Fragment() {

	private var _bind: FragmentCounterBinding? = null
	private val bind get() = _bind!!

	private val vm by lazy { ViewModelProvider(requireActivity())[CounterViewModel::class.java] }

	private var observer: Job? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentCounterBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bind.mcTab.setOnClickListener { findNavController().navigate(R.id.updateCounterFragment) }

	}

	override fun onStart() {
		super.onStart()
		lifecycleScope.launch {
			while (bind.container.width == 0) delay(1)
			bind.container.apply {
				val lp = layoutParams as RelativeLayout.LayoutParams
				lp.height = bind.container.width
				layoutParams = lp
			}
			bind.mcTab.apply {
				val lp = layoutParams as FrameLayout.LayoutParams
				lp.height = bind.container.width / 3
				lp.width = bind.container.width / 3
				layoutParams = lp
			}
		}
		observer = lifecycleScope.launch {
			launch {
				vm.label.collect {
					bind.label.text = it.toString()
					bind.container.apply {
						radius = it.toFloat() * requireContext().resources.getDimension(R.dimen.one_dp)
					}
				}
			}
		}
	}

	override fun onResume() {
		super.onResume()
		observer?.cancel()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_bind = null
	}


}