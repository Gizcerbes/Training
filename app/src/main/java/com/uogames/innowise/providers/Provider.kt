package com.uogames.innowise.providers

import com.uogames.innowise.providers.dto.Post
import com.uogames.innowise.providers.dto.UserInfo
import java.util.UUID

interface Provider {

	fun getUser(id: UUID): UserInfo?

	fun getPostInfo(id: UUID): Post?

	fun getPostByUserID(id: UUID): Post?

	fun getUserList(postID: Int? = null, limit: Int? = null, offset: Int? = null): List<UserInfo>

}