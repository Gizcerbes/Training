package com.uogames.innowise.ui.fragmnetColorimeter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uogames.innowise.databinding.FragmentColorimeterBinding

class ColorimeterFragment : Fragment() {


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return FragmentColorimeterBinding.inflate(inflater, container, false).root
	}


}