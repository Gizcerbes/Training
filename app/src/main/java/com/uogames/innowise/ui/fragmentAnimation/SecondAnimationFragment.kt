package com.uogames.innowise.ui.fragmentAnimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.uogames.innowise.R
import com.uogames.innowise.databinding.FragmentAnimationSecondBinding

class SecondAnimationFragment : Fragment() {

	private var _bind: FragmentAnimationSecondBinding? = null
	private val bind get() = _bind!!


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentAnimationSecondBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bind.btnAdd.setOnClickListener { bind.llContainer.addView(createImageView()) }
		bind.btnRemove.setOnClickListener {
			val size = bind.llContainer.size
			if (size > 0)
				bind.llContainer.removeViewAt(size - 1)
		}

	}

	private fun createImageView(): ImageView {
		val imageView = ImageView(requireContext()).apply {
			val size = requireContext().resources.getDimensionPixelOffset(R.dimen.image_size)
			layoutParams = LayoutParams(size, size)
		}
		Picasso.get().load("http://93.125.42.151:8084/files/schipperke.jpg").into(imageView)
		return imageView
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_bind = null
	}


}