package com.uogames.innowise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.uogames.innowise.ui.fragmentCounter.CounterViewModel
import com.uogames.innowise.ui.fragmentMultithread.MultithreadingViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

	private val vm by lazy { ViewModelProvider(this)[CounterViewModel::class.java] }
	private val multithreadingVM by lazy { ViewModelProvider(this)[MultithreadingViewModel::class.java] }

	private var toastObserver: Job? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.fragment_main)
		vm.load(applicationContext)
//		toastObserver = lifecycleScope.launch {
//			var counter = 0
//			multithreadingVM.message.collect {
//				val mes = if (counter++ % 4 == 3) "Surprise" else it
//				Toast.makeText(applicationContext, mes, Toast.LENGTH_SHORT).show()
//			}
//		}
	}


	override fun onStop() {
		super.onStop()
		vm.onStop()
		vm.save(applicationContext)
	}


	override fun onRestart() {
		super.onRestart()
		vm.onRestart()
	}

	override fun onDestroy() {
		super.onDestroy()
		toastObserver?.cancel()
	}

}