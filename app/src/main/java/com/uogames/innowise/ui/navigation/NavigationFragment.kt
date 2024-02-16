package com.uogames.innowise.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uogames.innowise.R
import com.uogames.innowise.databinding.FragmentNavigationBinding

class NavigationFragment : Fragment() {

	private var _bind: FragmentNavigationBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentNavigationBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		_bind?.btnCentring?.setOnClickListener { findNavController().navigate(R.id.centringFragment) }
		_bind?.btnAndScroll?.setOnClickListener { findNavController().navigate(R.id.buttonAndScrollFragment) }
		_bind?.btnUsers?.setOnClickListener { findNavController().navigate(R.id.usersFragment) }
		_bind?.btnZorder?.setOnClickListener { findNavController().navigate(R.id.ZOrderFragment) }
		_bind?.btnColorimetr?.setOnClickListener { findNavController().navigate(R.id.colorimeterFragment) }
		_bind?.btnAnimationFirst?.setOnClickListener { findNavController().navigate(R.id.firstAnimationFragment) }
		_bind?.btnAnimationSecond?.setOnClickListener { findNavController().navigate(R.id.secondAnimationFragment) }
		_bind?.btnAnimationThird?.setOnClickListener { findNavController().navigate(R.id.thirdAnimationFragment) }
		_bind?.btnColoredView?.setOnClickListener { findNavController().navigate(R.id.coloredFragment) }
		_bind?.btnProgressBar?.setOnClickListener { findNavController().navigate(R.id.customProgressBarFragment) }
		_bind?.btnBottom?.setOnClickListener { findNavController().navigate(R.id.bottomFragment) }
		_bind?.btnCounter?.setOnClickListener { findNavController().navigate(R.id.counterFragment) }
		_bind?.btnUpdateCounter?.setOnClickListener { findNavController().navigate(R.id.updateCounterFragment) }
		_bind?.btnMultithreading?.setOnClickListener { findNavController().navigate(R.id.multithreadingFragment) }
		_bind?.btnPier?.setOnClickListener { findNavController().navigate(R.id.portFragment) }
		_bind?.btnRace?.setOnClickListener { findNavController().navigate(R.id.raceFragment) }
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_bind = null
	}

}