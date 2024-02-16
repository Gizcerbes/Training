package com.uogames.innowise.ui.fragmentPort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class PortViewModel : ViewModel() {

	enum class ShipType {
		BREAD, BANANA, CLOTHES
	}

	class Ship private constructor(
		val id: Int = (Math.random() * Int.MAX_VALUE).toInt(),
		val type: ShipType,
		val capacity: Int,
		var fullness: Int = 0,
		pathToGate: ((Ship) -> Unit)? = null
	) {


		companion object {
			private val capacities = listOf(10, 50, 100)
			fun generate(pathToGate: ((Ship) -> Unit)? = null): Ship {
				return Ship(
					type = ShipType.entries.random(),
					capacity = capacities.random(),
					pathToGate = pathToGate
				)
			}
		}

		init {
			thread(isDaemon = true) {
				val timeToGo = (Math.random() * 60000).toLong()
				TimeUnit.MILLISECONDS.sleep(timeToGo)
				pathToGate?.let { it(this) }
			}
		}

		/**
		 *  @return if you set more then it can have it will return count it can't get
		 */
		fun fill(items: Int): Int {
			val space = fullness + items
			return if (space > capacity) {
				fullness = capacity
				space - capacity
			} else {
				fullness = space
				0
			}
		}

		override fun toString(): String {
			return "ID = $id \nType = $type,\ncapacity = $capacity,\nfullness = $fullness"
		}

	}

	private val sea = LinkedList<Ship>()
	private val gate = ArrayBlockingQueue<Ship>(1)
	private val exitBread = ArrayBlockingQueue<Ship>(1)
	private val exitBanana = ArrayBlockingQueue<Ship>(1)
	private val exitClothes = ArrayBlockingQueue<Ship>(1)

	private val _countInSea = MutableStateFlow(sea.size)
	val countInSea = _countInSea.asStateFlow()
	private val _gateShip = MutableStateFlow<Ship?>(null)
	val gateShip = _gateShip.asStateFlow()
	private val _dispatcherShip = MutableStateFlow<Ship?>(null)
	val dispatcherShip = _dispatcherShip.asStateFlow()
	private val _exitBreadShip = MutableStateFlow<Ship?>(null)
	val exitBreadShip = _exitBreadShip.asStateFlow()
	private val _exitBananaShip = MutableStateFlow<Ship?>(null)
	val exitBananaShip = _exitBananaShip.asStateFlow()
	private val _exitClosesShip = MutableStateFlow<Ship?>(null)
	val exitClothesShip = _exitClosesShip.asStateFlow()
	private val _pierBread = MutableSharedFlow<Pair<Ship, Int>?>()
	val pierBread = _pierBread.asSharedFlow()
	private val _pierBanana = MutableSharedFlow<Pair<Ship, Int>?>()
	val pierBanana = _pierBanana.asSharedFlow()
	private val _pierClothes = MutableSharedFlow<Pair<Ship, Int>?>()
	val pierClothes = _pierClothes.asSharedFlow()

	private var generatorJob: Job? = null

	init {

		thread(name = "Dispatcher", isDaemon = true) {
			while (true) {
				val ship = gate.take()
				_dispatcherShip.compareAndSet(null, ship)
				_gateShip.compareAndSet(ship, null)
				_countInSea.value = sea.size
				when (ship.type) {
					ShipType.BREAD -> {
						exitBread.put(ship)
						_exitBreadShip.value = ship
					}

					ShipType.BANANA -> {
						exitBanana.put(ship)
						_exitBananaShip.value = ship
					}

					ShipType.CLOTHES -> {
						exitClothes.put(ship)
						_exitClosesShip.value = ship
					}
				}
				_dispatcherShip.value = null
			}
		}
		thread(name = "pier bread", isDaemon = true) {
			while (true) {
				val ship = exitBread.take()
				_exitBreadShip.compareAndSet(ship, null)
				viewModelScope.launch { _pierBread.emit(ship to 0) }
				while (ship.fullness < ship.capacity) {
					TimeUnit.SECONDS.sleep(1)
					ship.fullness += 10
					viewModelScope.launch { _pierBread.emit(ship to ship.fullness * 100 / ship.capacity) }
				}
				viewModelScope.launch { _pierBread.emit(null) }
			}
		}
		thread(name = "pier banana", isDaemon = true) {
			while (true) {
				val ship = exitBanana.take()
				_exitBananaShip.compareAndSet(ship, null)
				viewModelScope.launch { _pierBanana.emit(ship to 0) }
				while (ship.fullness < ship.capacity) {
					TimeUnit.SECONDS.sleep(1)
					ship.fullness += 10
					viewModelScope.launch { _pierBanana.emit(ship to ship.fullness * 100 / ship.capacity) }
				}
				viewModelScope.launch { _pierBanana.emit(null) }
			}
		}
		thread(name = "pier clothes", isDaemon = true) {
			while (true) {
				val ship = exitClothes.take()
				_exitClosesShip.compareAndSet(ship, null)
				viewModelScope.launch { _pierClothes.emit(ship to 0) }
				while (ship.fullness < ship.capacity) {
					TimeUnit.SECONDS.sleep(1)
					ship.fullness += 10
					viewModelScope.launch { _pierClothes.emit(ship to ship.fullness * 100 / ship.capacity) }
				}
				viewModelScope.launch { _pierClothes.emit(null) }
			}
		}
	}

	fun startGenerator() {
		generatorJob?.cancel()
		generatorJob = viewModelScope.launch {
			while (true) {
				delay((Math.random() * 1000).toLong() + 1000)
				Ship.generate {
					courseToGate(it)
				}.also {
					sea.add(it)
					_countInSea.value = sea.size
				}
			}
		}
	}

	fun stopGenerator() {
		generatorJob?.cancel()
	}

	private fun courseToGate(ship: Ship) {
		gate.put(ship)
		sea.remove(ship)
		_gateShip.value = ship
	}


}