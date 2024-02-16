package com.uogames.innowise.ui.fragmentUsers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uogames.innowise.providers.dto.UserInfo
import com.uogames.innowise.ui.customView.CardProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UsersAdapter(
	private val callSize: () -> Int,
	private val callUser: (position: Int) -> UserInfo
) : RecyclerView.Adapter<UsersAdapter.Holder>() {

	private val recyclerScope = CoroutineScope(Dispatchers.Main)

	private val _cardOnClick = MutableSharedFlow<UserInfo>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
	val cardOnClick = _cardOnClick.asSharedFlow()

	inner class Holder(private val view: CardProfile) : RecyclerView.ViewHolder(view) {


		fun show(position: Int) {
			val user = callUser(position)
			Picasso.get().load(user.avatarULR).into(view.avatar)
			view.username.text = user.username
			view.setOnClickListener { recyclerScope.launch { _cardOnClick.emit(user) } }
		}

		fun onDestroy(){
			view.setOnClickListener(null)
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
		return Holder(CardProfile(parent.context))
	}

	override fun getItemCount(): Int = callSize()

	override fun onBindViewHolder(holder: Holder, position: Int) = holder.show(position)

	override fun onViewRecycled(holder: Holder) {
		holder.onDestroy()
		super.onViewRecycled(holder)
	}

//	override fun onViewDetachedFromWindow(holder: Holder) {
//		holder.onDestroy()
//		super.onViewDetachedFromWindow(holder)
//	}





}