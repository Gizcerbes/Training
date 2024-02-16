package com.uogames.innowise.ui.fragmentMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uogames.innowise.databinding.FragmentMainBinding

class MainFragment : Fragment() {

	private var _bind: FragmentMainBinding? = null
	val bind get() = _bind!!


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentMainBinding.inflate(layoutInflater, container, false)
		return _bind?.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_bind = null
	}

}