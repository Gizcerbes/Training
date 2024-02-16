package com.uogames.innowise.ui.fragmentBottom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uogames.innowise.R
import com.uogames.innowise.databinding.CardUserTtBinding
import com.uogames.innowise.providers.dto.Sex
import com.uogames.innowise.providers.dto.User

class UsersSecondAdapter (
	private val callSize: () -> Int,
	private val callUser: (position: Int) -> User
) : RecyclerView.Adapter<UsersSecondAdapter.Holder>() {


	inner class Holder(private val bind: CardUserTtBinding) : RecyclerView.ViewHolder(bind.root) {

		@SuppressLint("SetTextI18n")
		fun show(position: Int) {
			val user = callUser(position)
			Picasso.get().load(user.squareAvatarUrl).into(bind.ivAvatar)
			bind.ivSex.setImageResource(
				when (user.sex){
					Sex.MALE -> R.drawable.male_24px
					Sex.FEMALE -> R.drawable.female_24px
					Sex.OTHER -> R.drawable.agender_24px
				}
			)
			bind.tvFirstLastName.text = "${user.username} ${user.lastname}"
			bind.tvAge.text = user.age.toString()
			bind.tvDescription.text = user.description
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
		return Holder(CardUserTtBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}

	override fun getItemCount(): Int = callSize()

	override fun onBindViewHolder(holder: Holder, position: Int) {
		holder.show(position)
	}

}