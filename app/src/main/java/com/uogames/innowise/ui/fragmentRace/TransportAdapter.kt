package com.uogames.innowise.ui.fragmentRace

import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.uogames.innowise.R
import com.uogames.innowise.ui.customView.CardTransport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TransportAdapter(
	private val callCount: () -> Int,
	private val callItem: (position: Int) -> Transport,
	private val isDeletable: Boolean = false
) : RecyclerView.Adapter<TransportAdapter.Holder>() {

	private val recyclerScope = CoroutineScope(Dispatchers.Main)

	private val _deleteFlow = MutableSharedFlow<Int>()
	val deleteFlow = _deleteFlow.asSharedFlow()

	inner class Holder(private val view: CardTransport) : RecyclerView.ViewHolder(view) {

		fun show() {
			val transport = callItem(adapterPosition)
			show(transport)

		}

		private fun show(transport: Transport) {
			view.speed = transport.speed
			view.punctureRisk = transport.punctureRisk
			view.numberID.text = transport.id.toString()
			when (transport) {
				is Truck -> {
					view.setImageRes(R.drawable.local_shipping_24px)
					view.extraName = "Cargo"
					view.extra = transport.cargo.toString()
				}

				is Car -> {
					view.setImageRes(R.drawable.car)
					view.extraName = "Passengers"
					view.extra = transport.passengers.toString()
				}

				is Motorcycle -> {
					view.setImageRes(R.drawable.two_wheeler_24px)
					view.extraName = if (transport.hasStroller) "Has stroller" else ""
					view.extra = ""
				}

				else -> R.drawable.ic_launcher_foreground
			}
			view.setOnClickListener {
				if (!isDeletable) return@setOnClickListener
				val pop = PopupMenu(view.context, view).apply {
					val del = menu.add(view.context.getString(R.string.delete))
					del.setOnMenuItemClickListener {
						recyclerScope.launch { _deleteFlow.emit(adapterPosition) }
						true
					}
				}
				pop.show()
			}

		}


	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
		return Holder(CardTransport(parent.context).apply {
			showPath = false
			showStatus = false
			progressView.visibility = View.GONE
		})
	}

	override fun getItemCount(): Int = callCount()

	override fun onBindViewHolder(holder: Holder, position: Int) {
		holder.show()
	}


}