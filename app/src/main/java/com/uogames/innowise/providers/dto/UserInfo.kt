package com.uogames.innowise.providers.dto

import java.util.UUID

data class UserInfo(
	val id: UUID,
	val username: String,
	val lastTime: Long,
	val avatarULR: String,
)