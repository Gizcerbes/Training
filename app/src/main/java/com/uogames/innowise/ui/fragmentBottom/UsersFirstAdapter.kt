package com.uogames.innowise.ui.fragmentBottom

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uogames.innowise.R
import com.uogames.innowise.databinding.CardUserTsBinding
import com.uogames.innowise.providers.dto.Sex
import com.uogames.innowise.providers.dto.User

class UsersFirstAdapter(
	private val callSize: () -> Int,
	private val callUser: (position: Int) -> User?
) : RecyclerView.Adapter<UsersFirstAdapter.Holder>() {


	inner class Holder(private val bind: CardUserTsBinding) : RecyclerView.ViewHolder(bind.root) {

		@SuppressLint("SetTextI18n")
		fun show(position: Int) {
			val user = callUser(position)
			itemView.visibility = if (user == null) View.GONE else View.VISIBLE
			if (user == null) return

			Picasso.get().load(user.squareAvatarUrl).into(bind.ivAvatar)
			bind.ivSex.setImageResource(
				when (user.sex) {
					Sex.MALE -> R.drawable.male_24px
					Sex.FEMALE -> R.drawable.female_24px
					Sex.OTHER -> R.drawable.agender_24px
				}
			)
			bind.tvFirstLastName.text = "${user.username} ${user.lastname}"
			bind.tvAge.text = user.age.toString()
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
		return Holder(CardUserTsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}

	override fun getItemCount(): Int = callSize()

	override fun onBindViewHolder(holder: Holder, position: Int) {
		holder.show(position)
	}

}