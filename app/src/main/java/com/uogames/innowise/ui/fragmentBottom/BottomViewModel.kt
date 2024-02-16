package com.uogames.innowise.ui.fragmentBottom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uogames.innowise.R
import com.uogames.innowise.providers.dto.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import net.datafaker.Faker

class BottomViewModel : ViewModel() {

	private val scope = CoroutineScope(Dispatchers.IO)

	var selectedTab = MutableStateFlow<Int?>(null)

	data class Event(
		var selectedTab: Int? = null,
		var isLoading: Boolean = false,
		var isFirstLoad: Boolean = true,
		var size: Int = 0
	)

	private val faker = Faker()

	val userListFirst = User.generate()
	val userListSecond = User.generate()
	val adapterFirst = UsersFirstAdapter(
		callSize = { userListFirst.size },
		callUser = { userListFirst[it] }
	)
	val adapterSecond = UsersSecondAdapter(
		callSize = { userListSecond.size },
		callUser = { userListSecond[it] }
	)

	val texts = listOf(
		faker.job().position(),
		faker.job().position(),
		faker.job().position(),
		faker.job().position(),
		faker.job().position(),
		faker.job().position()
	)

	@OptIn(ExperimentalStdlibApi::class)
	val colors = listOf(
		faker.color().hex().replace("#", "FF").hexToInt(),
		faker.color().hex().replace("#", "FF").hexToInt(),
		faker.color().hex().replace("#", "FF").hexToInt(),
		faker.color().hex().replace("#", "FF").hexToInt(),
		faker.color().hex().replace("#", "FF").hexToInt(),
		faker.color().hex().replace("#", "FF").hexToInt()
	)

	val icons = listOf(
		R.drawable.agender_24px,
		R.drawable.baseline_add_24,
		R.drawable.baseline_favorite_24,
		R.drawable.baseline_visibility_24,
		R.drawable.female_24px,
		R.drawable.male_24px

	)

	val roleAdapter = RoleAdapter(
		callSize = { 20 },
		callItem = { texts[it % 6] },
		callColor = { colors[it % 6] },
		callImageResources = { icons[it % 6] }
	)

	val asymAdapter = AsymmetricAdapter(
		callSize = { 20 },
		callItem = { texts[it % 6] },
		callColor = { colors[it % 6] },
		callImageResources = { icons[it % 6] }
	)

	private val userData = HashMap<Int, User>()
	private val eventValue = MutableStateFlow(0)
	private val _isLoading = MutableStateFlow(false)
	private val _isFirstLoading = MutableStateFlow(true)
	private val _size = MutableStateFlow(0)
	val event = eventValue.map { Event() }
		.combine(selectedTab){ f,s -> f.apply { selectedTab = s } }
		.combine(_isLoading) { f, s -> f.apply { isLoading = s } }
		.combine(_size) { f, s -> f.apply { size = s } }
		.combine(_isFirstLoading) { f, s -> f.apply { isFirstLoad = s } }



	val adapterFive = UsersFirstAdapter(
		callSize = { userData.size },
		callUser = { userData[it] }
	)

	private var loadJob: Job? = null


	fun load() {
		if (_isFirstLoading.value) load(6000)
	}

	fun refresh() {
		load(3000)
	}

	fun clear() {
		_isLoading.value = false
		loadJob?.cancel()
		userData.clear()
		_size.value = userData.size
		scope.launch(Dispatchers.Main) { adapterFive.notifyDataSetChanged() }
		//eventValue.value++
	}

	private fun load(delay: Long) {
		_isLoading.value = false
		loadJob?.cancel()
		loadJob = scope.launch {
			runCatching {
				_isLoading.value = true
				delay(delay)
				val users = User.generate()
				userData.clear()
				users.forEachIndexed { i, u -> userData[i] = u }
				_size.value = userData.size
				launch(Dispatchers.Main) { adapterFive.notifyDataSetChanged() }
				_isFirstLoading.value = false
				_isLoading.value = false
				//eventValue.value++
			}
		}
	}


}