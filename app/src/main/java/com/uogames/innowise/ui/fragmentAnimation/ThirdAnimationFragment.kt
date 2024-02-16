package com.uogames.innowise.ui.fragmentAnimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.uogames.innowise.databinding.FragmentAnimationThirdBinding
import kotlinx.coroutines.Job

class ThirdAnimationFragment : Fragment() {

	private var _bind: FragmentAnimationThirdBinding? = null
	private val bind get() = _bind!!

	private val duration = 1000
	private var targetAlpha = 1f

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentAnimationThirdBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		bind.btnAddAlpha.setOnClickListener {
			targetAlpha += 0.1f
			startTransformation()
		}
		bind.btnRemoveAlpha.setOnClickListener {
			targetAlpha -= 0.1f
			startTransformation()
		}
		Picasso.get()
			.load("http://93.125.42.151:8084/files/schipperke.jpg")
			.into(bind.ivImage)
	}

	private fun startTransformation() {
		if (targetAlpha > 1) targetAlpha = 1f
		if (targetAlpha < 0) targetAlpha = 0f
		bind.ivImage.animate()
			.alpha(targetAlpha)
			.setDuration(duration.toLong())
			.start()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_bind = null
	}


}