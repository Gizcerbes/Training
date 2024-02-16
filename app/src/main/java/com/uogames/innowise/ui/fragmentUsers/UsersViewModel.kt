package com.uogames.innowise.ui.fragmentUsers

import androidx.lifecycle.ViewModel
import com.uogames.innowise.providers.Provider
import com.uogames.innowise.providers.ProviderImpl
import com.uogames.innowise.providers.dto.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class UsersViewModel : ViewModel() {

	private val provider: Provider = ProviderImpl

	private val users = MutableStateFlow<List<UserInfo>>(listOf())

	val adapter = UsersAdapter(
		callSize = { users.value.size },
		callUser = { users.value[it] }
	)

	val postListener = adapter.cardOnClick.map { provider.getPostByUserID(it.id) }

	init {
		provider.getUserList().also {
			users.value = it
			adapter.notifyDataSetChanged()
		}
	}


}