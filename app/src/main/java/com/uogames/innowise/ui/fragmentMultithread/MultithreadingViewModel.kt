package com.uogames.innowise.ui.fragmentMultithread

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MultithreadingViewModel : ViewModel() {

	private val startTime = System.currentTimeMillis()
	private val _session = MutableStateFlow<Long>(0)
	val session = _session.asStateFlow()

	private val _message = MutableSharedFlow<String>()
	val message = _message.asSharedFlow()

	init {
		thread(isDaemon = true) {
			var timeCorrector = startTime
			while (true) {
				val delay = timeCorrector + 1000 - System.currentTimeMillis()
				timeCorrector += 1000
				TimeUnit.MILLISECONDS.sleep(delay)

				_session.value = (System.currentTimeMillis() - startTime) / 1000
			}
		}
		thread(isDaemon = true) {
			// it should work after. Not before
			var timeCorrector = startTime + 1
			while (true) {
				val delay = timeCorrector + 10000 - System.currentTimeMillis()
				timeCorrector += 10000
				TimeUnit.MILLISECONDS.sleep(delay)

				runBlocking { _message.emit(_session.value.toString()) }
			}
		}

	}


}