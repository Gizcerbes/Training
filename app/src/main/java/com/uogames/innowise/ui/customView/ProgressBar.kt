package com.uogames.innowise.ui.customView

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.uogames.innowise.R


class ProgressBar(
	context: Context,
	attrs: AttributeSet? = null,
) : View(context, attrs) {


	private var progress = 0.7f
	private var percentagePaddingBody = 0.1f
	private var paddingIndicator = 0.1f
	private var strokeWidthBody = 40f
	private var strokeWidthIndicator = 80f
	private var bodyColor: Int = (0x8031888F).toInt()
	private var indicatorColor = (0xFF31888F).toInt()
	private var textColor = (0xff000000).toInt()
	private var textSize = 100.0f
	private var animateDuration = 1000L

	private val oval = RectF()
	val textBounds = Rect()
	private val paint = Paint()
	private var animator: ValueAnimator? = null

	init {
		context.theme.obtainStyledAttributes(
			attrs,
			R.styleable.ProgressBar, 0, 0
		).apply {
			progress = getFloat(R.styleable.ProgressBar_progress, progress)
			percentagePaddingBody = getFloat(R.styleable.ProgressBar_percentagePaddingBody, percentagePaddingBody)
			paddingIndicator = getFloat(R.styleable.ProgressBar_percentagePaddingIndicator, paddingIndicator)
			strokeWidthBody = getDimension(R.styleable.ProgressBar_strokeWidthBody, strokeWidthBody)
			strokeWidthIndicator = getDimension(R.styleable.ProgressBar_strokeWidthIndicator, strokeWidthIndicator)
			bodyColor = getColor(R.styleable.ProgressBar_bodyColor, bodyColor)
			indicatorColor = getColor(R.styleable.ProgressBar_indicatorColor, indicatorColor)
			textColor = getColor(R.styleable.ProgressBar_textColor, textColor)
			textSize = getDimension(R.styleable.ProgressBar_textSize, textSize)
			animateDuration = getInt(R.styleable.ProgressBar_animeteDuration, animateDuration.toInt()).toLong()
		}.recycle()

	}

	fun setProgress(float: Float) {
		animator?.cancel()
		animator = ValueAnimator.ofFloat(progress, float)
		animator?.setDuration(animateDuration)
		animator?.interpolator = AccelerateInterpolator()
		animator?.addUpdateListener {
			progress = it.animatedValue as Float
			invalidate()
		}
		animator?.start()

	}


	override fun onDraw(canvas: Canvas) {
		val mr = if (width > height) height.toFloat() else width.toFloat()
		val center = mr / 2
		val radiusBody = center - (mr * paddingIndicator)
		val radiusIndicator = center - (mr * percentagePaddingBody)

		paint.color = bodyColor
		paint.strokeWidth = strokeWidthBody
		paint.style = Paint.Style.STROKE

		paint.isAntiAlias = false
		oval.set(center - radiusBody, center - radiusBody, center + radiusBody, center + radiusBody)
		canvas.drawArc(oval, -90f, 360f, true, paint)


		paint.color = indicatorColor
		paint.strokeWidth = strokeWidthIndicator
		oval.set(center - radiusIndicator, center - radiusIndicator, center + radiusIndicator, center + radiusIndicator)

		canvas.drawArc(oval, -90f, 360 * progress, false, paint)

		var text = (progress * 100).toInt().toString()
		paint.isAntiAlias = true
		paint.color = textColor
		paint.textSize = textSize
		paint.strokeWidth = 2.0f
		paint.style = Paint.Style.FILL

		val textWidth = paint.measureText(text)

		paint.getTextBounds(text, 0, 1, textBounds)

		val textHeight = textBounds.height().toFloat()

		val x: Float = mr / 2 - textWidth / 2
		val y: Float = mr / 2 + textHeight / 2

		canvas.drawText(text, x, y, paint)


	}

}