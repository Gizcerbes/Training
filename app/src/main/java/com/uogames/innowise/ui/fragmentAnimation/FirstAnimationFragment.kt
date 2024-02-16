package com.uogames.innowise.ui.fragmentAnimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.uogames.innowise.databinding.FragmentAnimationFirstBinding

class FirstAnimationFragment : Fragment() {


	private var _bind: FragmentAnimationFirstBinding? = null
	private val bind get() = _bind!!


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentAnimationFirstBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bind.btnHide.setOnClickListener {
			bind.ivImage.visibility = View.GONE
			bind.tvText.visibility = View.VISIBLE
		}
		bind.btnShow.setOnClickListener {
			bind.ivImage.visibility = View.VISIBLE
			bind.tvText.visibility = View.GONE
		}
		Picasso.get()
			.load("http://93.125.42.151:8084/files/schipperke.jpg")
			.into(bind.ivImage)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_bind = null
	}

}