package com.uogames.innowise.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.uogames.innowise.databinding.CardRecommenrationBinding

class CardRecommendation(
	context: Context?,
	attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

	private val bind = CardRecommenrationBinding.inflate(LayoutInflater.from(context), this, false)
	val recommended get() = bind.tvRecommended
	val labelRecommended get() = bind.labelRecommended
	val viewed get() = bind.btnViewed
	val commented get() = bind.btnCommented
	val liked get() = bind.btnLiked
	val followers get() = bind.llRecommendedIcons

	init {
		addView(bind.root)
	}


}