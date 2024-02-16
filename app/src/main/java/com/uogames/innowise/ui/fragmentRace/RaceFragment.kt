package com.uogames.innowise.ui.fragmentRace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.uogames.innowise.R
import com.uogames.innowise.databinding.FragmentRaceBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RaceFragment : Fragment() {

	private var _bind: FragmentRaceBinding? = null
	private val bind get() = _bind!!

	private val vm by lazy { ViewModelProvider(requireActivity())[RaceViewModel::class.java] }

	private var observers: Job? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentRaceBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bind.bottomAppBar.setOnMenuItemClickListener {
			when (it.itemId) {
				R.id.add -> {
					RaceDialog.show(requireContext()) { result -> vm.addTransport(result) }
					true
				}

				R.id.stop -> {
					vm.stop()
					true
				}

				else -> false
			}
		}
		bind.btnStart.setOnClickListener { vm.run() }

	}

	override fun onStart() {
		super.onStart()
		observers = lifecycleScope.launch {
			launch {
				vm.isRunning.collect {
					bind.btnStart.isEnabled = !it
					if (it) {
						bind.rvTransport.layoutManager = GridLayoutManager(requireContext(), 1)
						bind.rvTransport.adapter = vm.raceAdapter
					} else {
						bind.rvTransport.layoutManager = GridLayoutManager(requireContext(), 2)
						bind.rvTransport.adapter = vm.transportAdapter
						if (vm.resultList.size != 0) {
							RaceDialogResult.show(requireContext(), vm.resultList.toTypedArray(),
								repeat = { vm.run() },
								cancel = { vm.reset() }
							)
							vm.reset()
						}
					}
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
		_bind?.rvTransport?.adapter = null
		_bind = null
	}


}