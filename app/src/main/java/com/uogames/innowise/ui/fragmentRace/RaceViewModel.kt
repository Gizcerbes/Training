package com.uogames.innowise.ui.fragmentRace

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class RaceViewModel : ViewModel() {

	var transportsList = ArrayList<Transport>()
		private set

	var resultList = ArrayList<Pair<Float, Transport>>()
		private set


	private var drivers = ArrayList<Thread>()

	private val path = 10000

	private val _countDown = MutableSharedFlow<Int>()
	val countDown = _countDown.asSharedFlow()

	private val _resultsFlow = MutableSharedFlow<ArrayList<Pair<Float, Transport>>>()
	val resultFlow = _resultsFlow.map { it.apply { sortByDescending { element -> element.first } } }

	private val _isRunning = MutableStateFlow(false)
	val isRunning = _isRunning.asStateFlow()

	private val _forceStop = MutableStateFlow(false)
	val forceStop = _forceStop.asStateFlow()

	val transportAdapter = TransportAdapter(
		callCount = { transportsList.size },
		callItem = { transportsList[it] },
		isDeletable = true
	)

	val raceAdapter = RaceAdapter(
		callSize = { transportsList.size },
		callItem = { transportsList[it] },
		callDistance = { path }
	)

	init {
		viewModelScope.launch {
			transportAdapter.deleteFlow.collect {
				transportsList.removeAt(it)
				transportAdapter.notifyDataSetChanged()
			}
		}

	}

	private fun prepare() {
		transportsList.forEach {
			it.reset()
			drivers.add(
				thread(start = false, isDaemon = true) {
					var spend = 0f
					var endCorrection = 0f
					while (it.path < path && !_forceStop.value) {
						TimeUnit.MILLISECONDS.sleep(100)
						endCorrection = it.step(path)
						spend += 1
					}
					resultList.add((spend + endCorrection) to it)
					viewModelScope.launch {
						_resultsFlow.emit(resultList)
						finish()
					}
				}
			)
		}
	}

	@Synchronized
	private fun finish(){
		_isRunning.value = resultList.size != transportsList.size
	}

	fun run() {
		viewModelScope.launch {
			reset()
			prepare()
			if (drivers.size == 0) return@launch
			_isRunning.value = true
			for (i in 3 downTo 1) _countDown.emit(i)
			drivers.parallelStream().forEach { it.start() }
			drivers.clear()
		}
	}

	fun reset() {
		transportsList.forEach { it.reset() }
		resultList.clear()
		_forceStop.value = false
	}

	fun stop() {
		_forceStop.value = true
	}

	fun addTransport(result: RaceDialog.Result) {
		if (isRunning.value) return
		when (result.type) {
			RaceDialog.TransportTypes.TRACK -> result.toTruck()
			RaceDialog.TransportTypes.CAR -> result.toCar()
			RaceDialog.TransportTypes.MOTORCYCLE -> result.toMotorcycle()
		}.let { transportsList.add(it) }
		transportAdapter.notifyDataSetChanged()
	}


	private fun RaceDialog.Result.toTruck(): Truck = Truck(
		speed,
		punctureRisk,
		runCatching { extras.getOrNull(0)?.second?.toInt() }.getOrNull() ?: 0
	)

	private fun RaceDialog.Result.toCar(): Car = Car(
		speed,
		punctureRisk,
		runCatching { extras.getOrNull(0)?.second?.toInt() }.getOrNull() ?: 0
	)

	private fun RaceDialog.Result.toMotorcycle(): Motorcycle = Motorcycle(
		speed,
		punctureRisk,
		extras.getOrNull(0) != null
	)

}