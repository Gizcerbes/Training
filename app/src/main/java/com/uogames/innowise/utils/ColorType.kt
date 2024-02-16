package com.uogames.innowise.utils

object ColorType {

	data class ARGB(val a: Int, val r: Int, val g: Int, val b: Int)

	fun transformARGB(color: Int): ARGB {
		val long = color.toLong()
		return ARGB(
			a = long.and(4278190080L).shr(24).toInt(),
			r = long.and(16711680).shr(16).toInt(),
			g = long.and(65280).shr(8).toInt(),
			b = long.and(255).toInt()
		)
	}

	fun isLight(color: Int): Boolean {
		val argb = transformARGB(color)
		return (argb.r > 255 / 2)
			.and(argb.g > 255 / 2)
			.and(argb.b > 255 / 2)
	}

	fun multiply(color: Int, cof: Float): Int {
		val argb = transformARGB(color)
		var r = argb.r * cof
		var g = argb.g * cof
		var b = argb.b * cof
		if (r > 255) r = 255f
		if (g > 255) g = 255f
		if (b > 255) b = 255f
		return argb.a.shl(24)
			.xor(r.toInt().shl(16))
			.xor(g.toInt().shl(8))
			.xor(b.toInt())
	}

}