package com.uogames.innowise.ui.fragmentCounter

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CounterViewModel : ViewModel() {
	companion object{
		private const val preferences = "FEDFDFEDFK"
		private const val LABEL = "LABEL"
	}

	private val _label = MutableStateFlow(0)
	val label = _label.asStateFlow()

	private var pointTime: Long = 0

	fun update() {
		_label.value += 10
	}

	fun onStop() {
		_label.value += 5
		pointTime = System.currentTimeMillis()
	}

	fun onRestart() {
		_label.value += 2
		val times = ((System.currentTimeMillis() - pointTime) / 60000) * 2
		_label.value -= times.toInt()
		if (_label.value < 0) _label.value = 0
	}

	fun save(context: Context){
		val sp = context.getSharedPreferences(preferences, Context.MODE_PRIVATE)
		val editor = sp.edit()
		editor.putInt(LABEL, _label.value)
		editor.apply()
	}

	fun load(context: Context){
		val sp = context.getSharedPreferences(preferences, Context.MODE_PRIVATE)
		_label.value = sp.getInt(LABEL, 0)
	}




}