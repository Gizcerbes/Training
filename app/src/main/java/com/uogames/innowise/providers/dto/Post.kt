package com.uogames.innowise.providers.dto

import java.util.UUID

data class Post(
	val id: UUID,
	val ownerID: UUID,
	val eventName: String,
	val imageURL: String,
	val allRecommendation: Int,
	val recommendation: Int,
	var userRecommendedList: List<UUID>,
	val viewed: Int,
	val messages: Int,
	val liked: Int
)