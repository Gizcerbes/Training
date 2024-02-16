package com.uogames.innowise.ui.fragmentButtonAndScroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uogames.innowise.databinding.FragmentButtonAndScrollBinding

class ButtonAndScrollFragment : Fragment() {


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return FragmentButtonAndScrollBinding.inflate(inflater, container, false).root
	}


}