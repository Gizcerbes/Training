package com.uogames.innowise.utils

inline fun <reified T : Any> Any.cast(): T? = this as? T