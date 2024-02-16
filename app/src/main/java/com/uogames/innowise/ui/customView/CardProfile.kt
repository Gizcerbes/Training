package com.uogames.innowise.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.uogames.innowise.R
import com.uogames.innowise.databinding.CardProfileBinding

class CardProfile(
	context: Context?,
	attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

	private val bind = CardProfileBinding.inflate(LayoutInflater.from(context), this, false)
	val avatar get() = bind.ivAvatar
	val username get() = bind.tvUsername
	val time get() = bind.tvTime
	val follow: View get() = bind.llFollow

	init {
		addView(bind.root)
		context?.theme?.obtainStyledAttributes(
			attrs,
			R.styleable.CardProfile, 0, 0
		)?.apply {
			getDrawable(R.styleable.CardProfile_android_src).run { avatar.setImageDrawable(this) }
			getText(R.styleable.CardProfile_username).run { username.text = this }
			getText(R.styleable.CardProfile_time).run { time.text = this }
		}?.recycle()


	}


}