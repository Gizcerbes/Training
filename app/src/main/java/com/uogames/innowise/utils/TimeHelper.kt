package com.uogames.innowise.utils

import android.content.Context
import com.uogames.innowise.R


object TimeHelper {

	fun buildLongStringTime(time: Long, context: Context): String {
		var timeToEnd = time
		if (timeToEnd < 0) timeToEnd = 0
		val days = timeToEnd / 86400000
		val hours = timeToEnd % 86400000 / 3600000
		val minutes = timeToEnd % 3600000 / 60000
		val sec = timeToEnd % 60000 / 1000
		val builder = StringBuilder()
		if (days > 0) builder.append("$days ${context.getString(R.string.days)}")
		else if (timeToEnd / 3600000 > 0) builder.append("$hours ${context.getString(R.string.hours)}")
		else if (timeToEnd / 60000 > 0) builder.append("$minutes ${context.getString(R.string.minutes)}")
		else builder.append("$sec ${context.getString(R.string.seconds)}")
		return builder.apply {
			append(" ${context.getString(R.string.ago)}")
		}.toString()
	}


}