package com.uogames.innowise.ui.fragmentPort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.uogames.innowise.databinding.FragmentPortBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PortFragment : Fragment() {

	private var _bind: FragmentPortBinding? = null
	private val bind get() = _bind!!

	private val vm by lazy { ViewModelProvider(requireActivity())[PortViewModel::class.java] }

	private var observers: Job? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentPortBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bind.tvPierBread.text = ""
		bind.tvPierBanana.text = ""
		bind.tvPierClothes.text = ""

		bind.btnStartGenerator.setOnClickListener { vm.startGenerator() }
		bind.btnStopGenerator.setOnClickListener { vm.stopGenerator() }
	}


	override fun onStart() {
		super.onStart()
		observers = lifecycleScope.launch {
			launch { vm.countInSea.collect { bind.tvCountInSea.text = it.toString() } }
			launch { vm.gateShip.collect { bind.tvGate.text = it?.toString().orEmpty() } }
			launch { vm.dispatcherShip.collect { bind.tvDispatcher.text = it?.toString().orEmpty() } }
			launch { vm.exitBreadShip.collect { bind.tvExitBread.text = it?.toString().orEmpty() } }
			launch { vm.exitBananaShip.collect { bind.tvExitBanana.text = it?.toString().orEmpty() } }
			launch { vm.exitClothesShip.collect { bind.tvExitClothes.text = it?.toString().orEmpty() } }
			launch {
				vm.pierBread.collect {
					bind.tvPierBread.text = it?.first?.toString().orEmpty()
					bind.lpiPierBread.progress = it?.second ?: 0
				}
			}
			launch {
				vm.pierBanana.collect {
					bind.tvPierBanana.text  = it?.first?.toString().orEmpty()
					bind.lpiPierBanana.progress = it?.second ?: 0
				}
			}
			launch {
				vm.pierClothes.collect {
					bind.tvPierClothes.text  = it?.first?.toString().orEmpty()
					bind.lpiPierClothes.progress = it?.second ?: 0
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
		_bind = null
	}


}