package com.uogames.innowise.ui.customView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.forEach
import com.google.android.material.card.MaterialCardView
import com.uogames.innowise.R

class CardFollowers(
	context: Context?,
	attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

	private var imageCount = 3
	private var imgSize = 75
	var cardColor: Int = 0
		set(value) {
			field = value
			forEach { (it as MaterialCardView).setCardBackgroundColor(value) }
		}
	private var urls: List<String> = listOf()
	private var _tv: TextView? = null
	var textColor: Long = 0xFF000000
		set(value) {
			field = value
			_tv?.setTextColor(value.toInt())
		}

	init {
		context?.theme?.obtainStyledAttributes(
			attrs,
			R.styleable.CardFollowers, 0, 0
		)?.apply {
			imageCount = getInt(R.styleable.CardFollowers_imageCount, 3)
			imgSize = getDimensionPixelOffset(R.styleable.CardFollowers_imageSize, 75)
			cardColor = getColor(R.styleable.CardFollowers_cardBackground, 0)
		}?.recycle()
	}

	fun setImages(urls: List<String>, urlViewChain: (url: String, imageView: ImageView) -> Unit) {
		if (imageCount < 1) return
		this.urls = urls

		for (i in 0 until imageCount) {
			if (i < urls.size) addView(addCard(urls[i], urlViewChain).apply { translationX = i * 0.5f * imgSize })
			else break
		}
		if (urls.size > imageCount) addView(addCountCard(urls.size - imageCount).apply {
			translationX = imageCount * 0.5f * imgSize + 0.25f * imgSize
		})

	}

	private fun addCard(url: String, urlViewChain: ((url: String, imageView: ImageView) -> Unit)? = null): MaterialCardView {
		return MaterialCardView(context).apply {
			layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
			radius = imgSize.toFloat()
			this.setCardBackgroundColor(cardColor)
			val iv = ImageView(context).apply {
				layoutParams = LayoutParams(imgSize, imgSize)
				if (urlViewChain == null) setImageURI(url.toUri())
				else urlViewChain(url, this)
			}
			addView(iv)
		}
	}

	@SuppressLint("SetTextI18n")
	private fun addCountCard(count: Int): MaterialCardView {
		return MaterialCardView(context).apply {
			layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
			radius = imgSize.toFloat()
			this.setCardBackgroundColor(cardColor)
			strokeWidth = 0
			_tv = TextView(context).apply {
				layoutParams = LayoutParams(imgSize, imgSize)
				gravity = Gravity.CENTER
				text = "+$count"
			}
			addView(_tv)
		}
	}


}