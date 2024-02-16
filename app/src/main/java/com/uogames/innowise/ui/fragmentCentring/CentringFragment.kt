package com.uogames.innowise.ui.fragmentCentring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uogames.innowise.databinding.FragmentCentringBinding

class CentringFragment: Fragment() {

	private var _bind: FragmentCentringBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentCentringBinding.inflate(inflater,container,false)
		return _bind?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_bind = null
	}

}