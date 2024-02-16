package com.uogames.innowise.ui.fragmentProfile

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.uogames.innowise.R
import com.uogames.innowise.databinding.FragmentProfileBinding
import com.uogames.innowise.utils.ColorType
import com.uogames.innowise.utils.TimeHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.UUID

class ProfileFragment : Fragment() {

	companion object {
		const val RESET = "ProfileFragment.RESET"
		const val POST_ID = "ProfileFragment.POST_ID"
	}

	private var _bind: FragmentProfileBinding? = null
	private val bind get() = _bind!!

	private val vm by lazy { ViewModelProvider(this.requireActivity())[ProfileViewModel::class.java] }

	private var observers: Job? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentProfileBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val id = arguments?.getString(POST_ID)?.let { UUID.fromString(it) } ?: return
		arguments?.getBoolean(RESET)?.also {
			if (it) vm.load(id)
		}
		bind.cpProfile.follow.setOnClickListener {
			Snackbar.make(requireView(), "Click follow", Snackbar.LENGTH_SHORT).show()
		}
		bind.ibBack.setOnClickListener { findNavController().popBackStack() }
		bind.btnMenu.setOnClickListener {
			val pop = PopupMenu(requireContext(), bind.btnMenu).apply {
				menu.add(requireContext().getString(R.string.this_is_the_menu))
			}
			pop.show()
		}
	}

	override fun onStart() {
		super.onStart()
		observers = lifecycleScope.launch {
			launch { vm.photo.collect { setLargePhoto(it) } }
			launch { vm.eventName.collect() { bind.tvEvent.text = it } }
			launch { vm.avatar.collect { Picasso.get().load(it).into(bind.cpProfile.avatar) } }
			launch { vm.username.collect { bind.cpProfile.username.text = it } }
			launch { vm.timeAgo.collect { bind.cpProfile.time.text = TimeHelper.buildLongStringTime(it, requireContext()) } }
			launch { vm.recommendation.collect { setRecommendation(it) } }
			launch { vm.viewed.collect { bind.crRecommendation.viewed.text = it.toString() } }
			launch { vm.messages.collect { bind.crRecommendation.commented.text = it.toString() } }
			launch { vm.liked.collect { bind.crRecommendation.liked.text = it.toString() } }
			launch {
				vm.urlList.collect {
					bind.crRecommendation.followers.setImages(it) { url, view ->
						Picasso.get().load(url).into(view)
					}
				}
			}
			launch {
				vm.errorHandle.collect {
					Snackbar.make(requireView(), it.message ?: it.javaClass.name, Snackbar.LENGTH_SHORT).show()
				}
			}
			launch {
				while (true) {
					vm.update()
					delay(5000)
				}
			}
		}
	}

	@SuppressLint("SetTextI18n")
	private fun setRecommendation(r: ProfileViewModel.Recommendation) {
		val textUsers = requireContext().getText(R.string.users)
		bind.crRecommendation.recommended.text = "${r.recommended} / ${r.allUsers} $textUsers"
	}

	private fun setLargePhoto(url: String?) {
		Picasso.get().load(url).into(bind.ivPostImage, object : Callback {

			override fun onSuccess() {
				lifecycleScope.launch {
					val bitmap = async(Dispatchers.IO) {
						val b = Picasso.get().load(url).get()
						Bitmap.createBitmap(b, 0, b.height - 10, b.width, 10)
					}.await()
					val palette = Palette.from(bitmap).generate()
					val dominant = palette.getDominantColor(requireContext().getColor(R.color.black))
					bind.backgr.setBackgroundColor(dominant)
					val isLight = ColorType.isLight(dominant)
					val dominantShade = ColorType.multiply(dominant, if (isLight) 0.8f else 1.2f)
					val first = requireContext().getColor(if (isLight) R.color.black else R.color.white)
					val second = requireContext().getColor(if (isLight) R.color.black_75 else R.color.white_75)
					bind.tvEvent.setTextColor(first)
					bind.cpProfile.username.setTextColor(first)
					bind.cpProfile.time.setTextColor(second)
					bind.crRecommendation.recommended.setTextColor(first)
					bind.crRecommendation.labelRecommended.setTextColor(first)
					bind.crRecommendation.liked.setTextColor(first)
					bind.crRecommendation.liked.setIconTintResource(if (isLight) R.color.black_75 else R.color.white_75)
					bind.crRecommendation.viewed.setTextColor(first)
					bind.crRecommendation.viewed.setIconTintResource(if (isLight) R.color.black_75 else R.color.white_75)
					bind.crRecommendation.commented.setTextColor(first)
					bind.crRecommendation.commented.setIconTintResource(if (isLight) R.color.black_75 else R.color.white_75)
					bind.crRecommendation.followers.cardColor = dominantShade
					bind.crRecommendation.followers.textColor = first.toLong()
				}

			}

			override fun onError(e: Exception?) {
			}

		})
	}


	override fun onStop() {
		super.onStop()
		observers?.cancel()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_bind = null
	}


}