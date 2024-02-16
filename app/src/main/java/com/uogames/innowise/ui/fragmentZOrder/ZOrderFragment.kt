package com.uogames.innowise.ui.fragmentZOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uogames.innowise.databinding.FragmentZOrserBinding

class ZOrderFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return FragmentZOrserBinding.inflate(inflater, container, false).root
	}


}