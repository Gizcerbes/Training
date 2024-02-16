package com.uogames.innowise.providers.dto

import net.datafaker.Faker
import kotlin.random.Random

enum class Sex {
	MALE, FEMALE, OTHER
}

data class User(
	val username: String,
	val lastname: String,
	val age: Int,
	val sex: Sex,
	val squareAvatarUrl: String = "https://image.cnbcfm.com/api/v1/image/105773423-1551716977818rtx6p9yw.jpg?v=1551717428&w=700&h=700",
	val description: String
) {

	companion object {

		fun generate(): List<User> = ArrayList<User>().apply {
			val faker = Faker()

			for (i in 0 until 30) {
				add(
					User(
						username = faker.name().firstName(),
						lastname = faker.name().lastName(),
						age = (Math.random() *84).toInt() + 16,
						sex = Sex.entries.random(),
						description = faker.leagueOfLegends().summonerSpell(),
						squareAvatarUrl = faker.avatar().image()
					)
				)
			}
		}

	}

}