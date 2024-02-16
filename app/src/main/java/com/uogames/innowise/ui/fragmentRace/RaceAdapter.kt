package com.uogames.innowise.ui.fragmentRace

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uogames.innowise.R
import com.uogames.innowise.ui.customView.CardTransport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RaceAdapter(
	private val callSize: () -> Int,
	private val callItem: (position: Int) -> Transport,
	private val callDistance: () -> Int
) : RecyclerView.Adapter<RaceAdapter.Holder>() {

	private val recyclerScope = CoroutineScope(Dispatchers.Main)

	inner class Holder(private val view: CardTransport) : RecyclerView.ViewHolder(view) {

		private var observer: Job? = null

		fun show() {
			val item = callItem(adapterPosition)
			show(item)
			observer = recyclerScope.launch {
				while (true) {
					show(callItem(adapterPosition))
					delay(10)
				}
			}
		}

		fun show(transport: Transport) {
			view.path = transport.path.toInt().toString()
			view.status = transport.status.toString()
			view.progress = transport.path / callDistance()
			view.numberID.text = transport.id.toString()
			when (transport) {
				is Truck -> {
					view.setImageRes(R.drawable.local_shipping_24px)
				}

				is Car -> {
					view.setImageRes(R.drawable.car)
				}

				is Motorcycle -> {
					view.setImageRes(R.drawable.two_wheeler_24px)
				}

				else -> R.drawable.ic_launcher_foreground
			}
		}

		fun onDestroy() {
			observer?.cancel()
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
		return Holder(CardTransport(parent.context).apply {
			showPath = true
			showStatus = true
			showExtra = false
			showSpeed = false
			showRisk = false
		})
	}

	override fun getItemCount(): Int = callSize()

	override fun onBindViewHolder(holder: Holder, position: Int) {
		holder.show()
	}

	override fun onViewRecycled(holder: Holder) {
		super.onViewRecycled(holder)
		holder.onDestroy()
	}

}