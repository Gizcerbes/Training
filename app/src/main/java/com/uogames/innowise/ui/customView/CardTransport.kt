package com.uogames.innowise.ui.customView

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import com.uogames.innowise.R
import com.uogames.innowise.databinding.CardTransportBinding

class CardTransport(
	context: Context?,
	attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

	private val bind = CardTransportBinding.inflate(LayoutInflater.from(context), this, false)

	var showIndicator = true
		set(value) {
			field = value
			bind.lpiProgress.visibility = if (value) VISIBLE else GONE
		}
	var showRisk = true
		set(value) {
			field = value
			bind.llPunctureRisk.visibility = if (value) VISIBLE else GONE
		}
	var showSpeed = true
		set(value) {
			field = value
			bind.llSpeed.visibility = if (value) VISIBLE else GONE
		}
	var showExtra = true
		set(value) {
			field = value
			bind.llExtra.visibility = if (value) VISIBLE else GONE
		}
	var showStatus = true
		set(value) {
			field = value
			bind.llStatus.visibility = if (value) VISIBLE else GONE
		}
	var showPath = true
		set(value) {
			field = value
			bind.llPath.visibility = if(value) VISIBLE else GONE
		}

	var transportType: String = ""
		set(value) {
			field = value
			bind.tvTransportType.text = value
		}
	var progress: Float = 0f
		set(value) {
			field = value
			bind.lpiProgress.progress = (value * 100).toInt()
		}
	var speed: Int = 0
		set(value) {
			field = value
			bind.tvMaxSpeed.text = value.toString()
		}
	var punctureRisk = 0
		set(value) {
			field = value
			bind.tvPictureRisk.text = value.toString()
		}
	var extraName = ""
		set(value) {
			field = value
			bind.labelExtra.text = value
		}
	var extra = ""
		set(value) {
			field = value
			bind.tvExtra.text = value
		}
	var status = ""
		set(value) {
			field = value
			bind.tvStatus.text = value
		}
	var path = ""
		set(value) {
			field = value
			bind.tvPath.text = value
		}

	val progressView = bind.lpiProgress
	val numberID = bind.tvNumber

	init {
		addView(bind.root)
		context?.theme?.obtainStyledAttributes(
			attrs,
			R.styleable.CardTransport, 0, 0
		)?.apply {
			showIndicator = getBoolean(R.styleable.CardTransport_showIndicator, showIndicator)
			showSpeed = getBoolean(R.styleable.CardTransport_showSpeed, showSpeed)
			showRisk = getBoolean(R.styleable.CardTransport_showRisk, showRisk)
			showExtra = getBoolean(R.styleable.CardTransport_showExtra, showExtra)
			showStatus = getBoolean(R.styleable.CardTransport_showStatus, showStatus)
			showPath = getBoolean(R.styleable.CardTransport_showPath, showPath)
			transportType = getString(R.styleable.CardTransport_transportType).orEmpty()
			progress = getFloat(R.styleable.CardTransport_progress, progress)
			speed = getInt(R.styleable.CardTransport_speed, speed)
			punctureRisk = getInt(R.styleable.CardTransport_punctureRisk, punctureRisk)
			extraName = getString(R.styleable.CardTransport_extraName).orEmpty()
			extra = getString(R.styleable.CardTransport_extra).orEmpty()
			status = getString(R.styleable.CardTransport_status).orEmpty()
			path = getString(R.styleable.CardTransport_path).orEmpty()
		}?.recycle()
	}


	fun setImageDrawable(r: Drawable?) {
		bind.ivType.setImageDrawable(r)
	}

	fun setImageRes(@DrawableRes res: Int) {
		bind.ivType.setImageResource(res)
	}

}