package com.uogames.innowise.ui.fragmentUsers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uogames.innowise.providers.dto.Post
import com.uogames.innowise.ui.fragmentProfile.ProfileFragment
import com.uogames.innowise.R
import com.uogames.innowise.databinding.FragmentUsersBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UsersFragment : Fragment() {


	private var _bind: FragmentUsersBinding? = null
	private val bind get() = _bind!!

	private val vm by lazy { ViewModelProvider(this.requireActivity())[UsersViewModel::class.java] }

	private var observers: Job? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentUsersBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}

	override fun onStart() {
		super.onStart()
		_bind?.rvAdapter?.adapter = vm.adapter
		observers = lifecycleScope.launch {
			launch { vm.postListener.collect { if (it != null) navigateProfile(it) } }
		}
	}

	private fun navigateProfile(post: Post) {
		findNavController().navigate(
			R.id.profileFragment, bundleOf(
				ProfileFragment.POST_ID to post.id.toString(),
				ProfileFragment.RESET to true
			)
		)
	}

	override fun onStop() {
		super.onStop()
		_bind?.rvAdapter?.adapter = null
		observers?.cancel()
	}

	override fun onDestroy() {
		super.onDestroy()
		_bind = null
	}


}