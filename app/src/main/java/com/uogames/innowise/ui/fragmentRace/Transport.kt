package com.uogames.innowise.ui.fragmentRace

abstract class Transport {

	enum class Status {
		STOPPED, RUNNING, REPAIRING
	}

	abstract val speed: Int

	abstract val punctureRisk: Int

	val id: Int = (Math.random() * Int.MAX_VALUE).toInt()

	var status: Status = Status.STOPPED
		private set

	var path: Float = 0f
		private set

	var damage: Float = 0f
		private set

	fun step(endPath: Int): Float {
		val oldPath = path
		when (status) {
			Status.RUNNING, Status.STOPPED -> run(endPath)
			Status.REPAIRING -> repair()
		}
		return (endPath - oldPath) / speed
	}

	private fun run(endPath: Int) {
		if (Math.random() * 100 <= punctureRisk) {
			damage = (Math.random() * 100).toFloat()
			status = Status.REPAIRING
		} else {
			path += speed
			status = if (endPath <= path) Status.STOPPED
			else Status.RUNNING
		}
	}

	private fun repair() {
		damage -= (Math.random() * 20).toFloat()
		if (damage <= 0) {
			damage = 0f
			status = Status.RUNNING
		}
	}

	fun reset() {
		damage = 0f
		path = 0f
		status = Status.STOPPED
	}

}

class Truck(
	override val speed: Int,
	override val punctureRisk: Int,
	val cargo: Int
) : Transport()

class Car(
	override val speed: Int,
	override val punctureRisk: Int,
	val passengers: Int
): Transport()

class Motorcycle(
	override val speed: Int,
	override val punctureRisk: Int,
	val hasStroller: Boolean
): Transport()