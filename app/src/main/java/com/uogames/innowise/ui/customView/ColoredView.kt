package com.uogames.innowise.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setMargins
import com.google.android.material.button.MaterialButton

class ColoredView(
	context: Context,
	attrs: AttributeSet? = null,
) : RelativeLayout(context, attrs) {

	val colorList = listOf(
		0xffff0000,
		0xff00ff00,
		0xff0000ff,
		0xffffff00,
		0xff00ffff,
		0xffff00ff,
		0xff800080,
		0xff800000,
		0xff808000,
		0xff008000
	).map { it.toInt() }

	var index = 0
		private set(value) {
			field = if (value >= 0) value else colorList.size - 1

		}

	init {

		val colorBox = ImageView(context).apply {
			val size = 300
			layoutParams = LayoutParams(size, size).apply {
				addRule(CENTER_IN_PARENT)
			}
			setBackgroundColor(colorList[1])
		}
		addView(colorBox)

		val previousID = (Math.random() * Int.MAX_VALUE).toInt()
		val nextID = (Math.random() * Int.MAX_VALUE).toInt()

		val previous = MaterialButton(context).apply {
			id = previousID
			text = "Previous"
			layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				this.addRule(ALIGN_PARENT_BOTTOM)
				setMargins(15)
			}
		}

		addView(previous)

		val next = MaterialButton(context).apply {
			id = nextID
			text = "Next"
			layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(ALIGN_PARENT_BOTTOM)
				addRule(ALIGN_PARENT_END)
				setMargins(15)
			}
		}
		addView(next)

		val textView = TextView(context).apply {
			layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(START_OF, next.id)
				addRule(END_OF, previous.id)
				addRule(ALIGN_TOP, previous.id)
				addRule(ALIGN_PARENT_BOTTOM)
				setMargins(15)
			}
			gravity = Gravity.CENTER
		}

		addView(textView)

		colorBox.setBackgroundColor(colorList[index % colorList.size])
		textView.text = (index % colorList.size).toString()

		previous.setOnClickListener {
			index--
			textView.text = (index % colorList.size).toString()
			colorBox.setBackgroundColor(colorList[index % colorList.size])
		}

		next.setOnClickListener {
			index++
			textView.text = (index % colorList.size).toString()
			colorBox.setBackgroundColor(colorList[index % colorList.size])
		}

	}


}