package com.uogames.innowise.providers

import com.uogames.innowise.providers.dto.Post
import com.uogames.innowise.providers.dto.UserInfo
import java.util.UUID

object ProviderImpl : Provider {

	private val users = HashMap<UUID, UserInfo>()
	private val postInfo = HashMap<UUID, Post>()

	init {
		createUserAndPost(
			UUID.randomUUID(),
			"schipperke__ginger",
			3454654L,
			"http://93.125.42.151:8084/files/ginger-avatar.jpg",
			UUID.randomUUID(),
			"It's so cold outside that we don't stay there long as Ginger need.",
			"http://93.125.42.151:8084/files/schipperke.jpg",
			456,
			290,
			listOf(),
			650,
			25,
			290
		)
		createUserAndPost(
			UUID.randomUUID(),
			"kot_murmot",
			443435,
			"http://93.125.42.151:8084/files/murmont-avatar.jpg",
			UUID.randomUUID(),
			"Winner of the year among relaxing cats.",
			"http://93.125.42.151:8084/files/murmont.jpg",
			12000,
			1000,
			listOf(),
			12000,
			2300,
			1300
		)
		createUserAndPost(
			UUID.randomUUID(),
			"escape_am",
			34545,
			"http://93.125.42.151:8084/files/escape_am-avatar.jpg",
			UUID.randomUUID(),
			"#photominsk #nightminsk #nemiga",
			"http://93.125.42.151:8084/files/escape_am.jpg",
			243453,
			323,
			listOf(),
			4344, 432, 430
		)
		createUserAndPost(
			UUID.randomUUID(),
			"FU MUGI ATSUSHI",
			435,
			"http://93.125.42.151:8084/files/FU-MUGI-ATSUSHI-avatar.jpg",
			UUID.randomUUID(),
			"おっさん座りと上目遣いのギャップが可愛",
			"http://93.125.42.151:8084/files/FU-MUGI-ATSUSHI.jpg",
			4325,
			4345,
			listOf(),
			4554,
			435,
			1634
		)
		createUserAndPost(
			UUID.randomUUID(),
			"Buddy",
			437,
			"http://93.125.42.151:8084/files/Buddy-avatar.jpg",
			UUID.randomUUID(),
			"Time to mow the grass",
			"http://93.125.42.151:8084/files/Buddy.jpg",
			5453, 434, listOf(), 43434, 423, 4545
		)
		createUserAndPost(
			UUID.randomUUID(),
			"Jenna Ortega",
			43,
			"http://93.125.42.151:8084/files/Jenna-Ortega-avatar.jpg",
			UUID.randomUUID(),
			"Oxygen no longer exists for me",
			"http://93.125.42.151:8084/files/Jenna-Ortega.jpg",
			434, 64, listOf(), 634, 31, 321
		)
		createUserAndPost(
			UUID.randomUUID(),
			"Karl Urban",
			64,
			"http://93.125.42.151:8084/files/Karl-Urban-avatar.jpg",
			UUID.randomUUID(),
			"Bye Bye Butcher Beard",
			"http://93.125.42.151:8084/files/Karl-Urban.jpg",
			4324, 53252, listOf(), 43, 64, 324
		)
		createUserAndPost(
			UUID.randomUUID(),
			"Leonardo DiCaprio",
			436,
			"http://93.125.42.151:8084/files/Leonardo-DiCaprio-avatar.jpg",
			UUID.randomUUID(),
			"Hope everyone has a great Friday",
			"http://93.125.42.151:8084/files/Leonardo-DiCaprio.jpg",
			34, 53, listOf(), 532, 31, 5
		)

		createUserAndPost(
			UUID.randomUUID(),
			"“Weird Al” Yankovic",
			3246,
			"http://93.125.42.151:8084/files/Weird-Al-Yankovic-avatar.jpg",
			UUID.randomUUID(),
			"Here’s a lovely piece of art from @jayjayzee (from @z2comics’s #TheIllustratedAl)",
			"http://93.125.42.151:8084/files/Weird-Al-Yankovic.jpg",
			4324, 535, listOf(), 534, 53, 5233
		)
		createUserAndPost(
			UUID.randomUUID(),
			"Anna Faris",
			545,
			"http://93.125.42.151:8084/files/Anna-Faris-avatar.jpg",
			UUID.randomUUID(),
			"#AnnaFaris @annafaris",
			"http://93.125.42.151:8084/files/Anna-Faris.jpg",
			23, 5353, listOf(), 534, 4235, 213
		)

		val users = users.toList().map { it.second.id }
		postInfo.forEach {
			it.value.userRecommendedList = users
		}

	}

	private fun createUserAndPost(
		uid: UUID,
		username: String,
		lastTime: Long,
		avatarULR: String,
		postID: UUID,
		eventName: String,
		imageURL: String,
		allRecommendation: Int,
		recommendation: Int,
		userRecommendedList: List<UUID>,
		viewed: Int,
		messages: Int,
		liked: Int
	) {
		users[uid] = UserInfo(
			uid,
			username,
			lastTime,
			avatarULR
		)
		postInfo[postID] = Post(
			postID,
			uid,
			eventName,
			imageURL,
			allRecommendation,
			recommendation,
			userRecommendedList,
			viewed,
			messages,
			liked
		)
	}

	override fun getUser(id: UUID): UserInfo? = users[id]

	override fun getPostInfo(id: UUID): Post? = postInfo[id]

	override fun getPostByUserID(id: UUID): Post? = postInfo.filter { it.value.ownerID == id }.toList().firstOrNull()?.second

	override fun getUserList(postID: Int?, limit: Int?, offset: Int?): List<UserInfo> = users.toList().map { it.second }


}