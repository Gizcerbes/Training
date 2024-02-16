package com.uogames.innowise.ui.fragmentRace

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uogames.innowise.R

object RaceDialogResult {


	fun show(
		context: Context,
		result: Array<Pair<Float, Transport>>,
		repeat: () -> Unit,
		cancel: () -> Unit
	) {
		val rv = RecyclerView(context)
		rv.adapter = TransportAdapter(
			callCount = { result.size },
			callItem = { result[it].second }
		)
		rv.layoutManager = GridLayoutManager(context, 1)

		MaterialAlertDialogBuilder(context)
			.setTitle(R.string.result)
			.setView(rv)
			.setPositiveButton(R.string.repeat) { _, _ -> repeat() }
			.setNegativeButton(R.string.cancel) { _, _ ->  cancel() }
			.show()
	}


}