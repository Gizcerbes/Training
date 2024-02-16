package com.uogames.innowise.ui.fragmentCounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uogames.innowise.databinding.FragmentUpdateCounterBinding

class UpdateCounterFragment : Fragment() {


	private var _bind: FragmentUpdateCounterBinding? = null
	private val bind get() = _bind!!

	private val vm by lazy { ViewModelProvider(requireActivity())[CounterViewModel::class.java] }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentUpdateCounterBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bind.btnUpdate.setOnClickListener {
			vm.update()
			findNavController().popBackStack()
		}
		bind.btnCancel.setOnClickListener { findNavController().popBackStack() }

	}

	override fun onDestroyView() {
		super.onDestroyView()
		_bind = null
	}

}