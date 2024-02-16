package com.uogames.innowise.ui.fragmentCustomProgressbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uogames.innowise.databinding.FragmentCustomProgressBinding

class CustomProgressBarFragment : Fragment() {


	private var _bind: FragmentCustomProgressBinding? = null
	private val bind get() = _bind!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentCustomProgressBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bind.btnSetRandom.setOnClickListener { bind.progress.setProgress(Math.random().toFloat()) }
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_bind = null
	}

}