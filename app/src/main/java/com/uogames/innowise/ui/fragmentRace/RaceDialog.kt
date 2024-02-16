package com.uogames.innowise.ui.fragmentRace

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.widget.addTextChangedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.uogames.innowise.R
import com.uogames.innowise.databinding.DialogFragmentBuildTransportBinding
import com.uogames.innowise.utils.cast

object RaceDialog {

	enum class TransportTypes(@DrawableRes val icon: Int) {
		TRACK(R.drawable.local_shipping_24px),
		CAR(R.drawable.car),
		MOTORCYCLE(R.drawable.two_wheeler_24px);
	}

	data class Result(val type: TransportTypes, val speed: Int, val punctureRisk: Int, val extras: List<Pair<String, String>>)

	fun show(context: Context, calBack: (Result) -> Unit) {
		val bind = DialogFragmentBuildTransportBinding.inflate(LayoutInflater.from(context))
		val stList = TransportTypes.entries.map { it.toString() }

		val actv = bind.tilType.editText?.cast<MaterialAutoCompleteTextView>()
		actv?.setSimpleItems(stList.toTypedArray())
		actv?.setText(stList.first(), false)

		bind.tilExtra.visibility = View.GONE

		bind.tilType.editText?.addTextChangedListener { bind.checkType() }
		bind.tilSpeed.editText?.addTextChangedListener { bind.tilSpeed.checkRange(1, 300)}
		bind.tilPunctureRisk.editText?.addTextChangedListener { bind.tilPunctureRisk.checkPunctureRisk() }
		bind.tilExtra.editText?.addTextChangedListener { bind.checkExtra() }
		bind.mcbAddExtra.setOnClickListener { bind.checkExtra() }

		val dialog = MaterialAlertDialogBuilder(context)
			.setView(bind.root)
			.setPositiveButton(R.string.apply, null)
			.setNegativeButton(R.string.cancel) { _, _ -> }
			.create()

		dialog.setOnShowListener {
			val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
			button.setOnClickListener {
				if (bind.hasErrors()) return@setOnClickListener
				calBack(bind.buildResult())
				dialog.dismiss()
			}
		}

		dialog.show()

	}


	private fun DialogFragmentBuildTransportBinding.getTransportType(): TransportTypes = runCatching {
		TransportTypes.valueOf(tilType.editText?.text.toString())
	}.getOrNull() ?: TransportTypes.TRACK


	private fun DialogFragmentBuildTransportBinding.buildResult(): Result = Result(
		type = getTransportType(),
		speed = tilSpeed.editText?.text?.toString()?.toInt() ?: 0,
		punctureRisk = tilPunctureRisk.editText?.text?.toString()?.toInt() ?: 0,
		extras = tilExtra.hint?.toString()?.let {
			if (mcbAddExtra.isChecked) listOf(it to tilExtra.editText?.text?.toString().orEmpty()) else listOf()
		} ?: listOf()
	)


	private fun DialogFragmentBuildTransportBinding.hasErrors(): Boolean {
		tilSpeed.checkRange(1, 300)
		tilPunctureRisk.checkPunctureRisk()
		checkExtra()
		return (tilSpeed.error != null)
			.or(tilPunctureRisk.error != null)
			.or(tilExtra.error != null)
	}

	private fun DialogFragmentBuildTransportBinding.checkExtra() {
		val type = getTransportType()
		if (mcbAddExtra.isChecked && type != TransportTypes.MOTORCYCLE) {
			tilExtra.visibility = View.VISIBLE
			when (type) {
				TransportTypes.TRACK -> tilExtra.checkRange(0, 100000)
				TransportTypes.CAR -> tilExtra.checkRange(0, 4)
				else -> {}
			}
		} else {
			tilExtra.visibility = View.GONE
			tilExtra.error = null
		}
	}

	private fun DialogFragmentBuildTransportBinding.checkType() {
		val type = getTransportType()
		tilExtra.hint = when(type){
			TransportTypes.TRACK -> "Cargo"
			TransportTypes.CAR -> "Passengers"
			TransportTypes.MOTORCYCLE -> "Has a stroller"
		}
		checkExtra()
	}


	private fun TextInputLayout.checkSpeed(): Boolean = try {
		error = null
		val r = editText?.text?.toString()?.toInt() ?: 0
		if (r <= 0) throw Exception("It should be higher than 0")
		true
	} catch (e: Exception) {
		error = e.message ?: e.javaClass.name
		false
	}

	private fun TextInputLayout.checkPunctureRisk(): Boolean = try {
		error = null
		val r = editText?.text?.toString()?.toInt() ?: 0
		if (r >= 100) throw Exception("It should be less than 100")
		true
	} catch (e: Exception) {
		error = e.message ?: e.javaClass.name
		false
	}

	private fun TextInputLayout.checkRange(start: Int, end: Int): Boolean = try {
		error = null
		val r = editText?.text?.toString()?.toInt() ?: 0
		if (r < start) throw Exception("It should be equal or higher than $start")
		else if (r > end) throw Exception("It should be equal or less than $end")
		true
	} catch (e: Exception) {
		error = e.message ?: e.javaClass.name
		false
	}


}