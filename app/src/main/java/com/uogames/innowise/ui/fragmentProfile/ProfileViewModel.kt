package com.uogames.innowise.ui.fragmentProfile

import androidx.lifecycle.ViewModel
import com.uogames.innowise.providers.Provider
import com.uogames.innowise.providers.ProviderImpl
import com.uogames.innowise.ui.Loadable
import com.uogames.innowise.ui.Resealable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.UUID

class ProfileViewModel : ViewModel(), Resealable, Loadable {

	private val scope = CoroutineScope(Dispatchers.IO)
	private val provider: Provider = ProviderImpl

	private val _photo = MutableStateFlow<String?>(null)
	val photo = _photo.asStateFlow()

	private val _eventName = MutableStateFlow("")
	val eventName = _eventName.asStateFlow()

	private val _avatar = MutableStateFlow<String?>(null)
	val avatar = _avatar.asStateFlow()

	private val _username = MutableStateFlow("")
	val username = _username.asStateFlow()

	private val _timeAgo = MutableStateFlow<Long>(0L)
	val timeAgo = _timeAgo.asStateFlow()


	class Recommendation(val allUsers: Int, val recommended: Int)

	private val _allRecommendation = MutableStateFlow(0)
	private val _recommended = MutableStateFlow(0)
	val recommendation = _allRecommendation.zip(_recommended) { a, b -> Recommendation(a, b) }


	private val _viewed = MutableStateFlow(0)
	val viewed = _viewed.asStateFlow()

	private val _messages = MutableStateFlow(0)
	val messages = _messages.asStateFlow()

	private val _liked = MutableStateFlow(0)
	val liked = _liked.asStateFlow()

	private val _urlsList = MutableStateFlow<List<String>>(listOf())
	val urlList = _urlsList.asStateFlow()

	private val _errorHandle = MutableSharedFlow<Throwable>()
	val errorHandle = _errorHandle.asSharedFlow()


	override fun reset() {
		_photo.value = null
		_eventName.value = ""
		_avatar.value = null
		_username.value = ""
		_timeAgo.value = 0
		_allRecommendation.value = 0
		_recommended.value = 0
		_viewed.value = 0
		_messages.value = 0
		_liked.value = 0
	}

	override fun load(id: UUID) {
		scope.launch {
			runCatching {
				val post = provider.getPostInfo(id) ?: throw Exception("Post not exists")
				val user = provider.getUser(post.ownerID) ?: throw Exception("User not exists")
				_photo.value = post.imageURL
				_eventName.value = post.eventName
				_viewed.value = post.viewed
				_messages.value = post.messages
				_liked.value = post.liked
				_allRecommendation.value = post.allRecommendation
				_recommended.value = post.recommendation
				_avatar.value = user.avatarULR
				_username.value = user.username
				_timeAgo.value = user.lastTime
				_urlsList.value = provider.getUserList().map { it.avatarULR }

			}.onFailure {
				_errorHandle.emit(it)
			}
		}
	}

	fun update() {
		scope.launch {
			runCatching {
				_allRecommendation.value = (_recommended.value * 1.25).toInt()
				_recommended.value = (_allRecommendation.value * Math.random()).toInt()
				_viewed.value = (_viewed.value * 1.25).toInt()
				_messages.value = (_messages.value * 1.1).toInt()
				_liked.value = (_viewed.value * 0.1).toInt()
				_timeAgo.value = (1000 * 60 * 60 * 24 * 2 * Math.random()).toLong()
			}
		}
	}


}