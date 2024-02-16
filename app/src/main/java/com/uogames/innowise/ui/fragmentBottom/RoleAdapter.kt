package com.uogames.innowise.ui.fragmentBottom

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.uogames.innowise.R
import com.uogames.innowise.databinding.CardMenuBinding
import com.uogames.innowise.utils.ColorType

class RoleAdapter(
	private val callSize: () -> Int,
	private val callItem: (position: Int) -> String,
	private val callColor: (position: Int) -> Int,
	private val callImageResources: (position: Int) -> Int
) : RecyclerView.Adapter<RoleAdapter.Holder>() {


	inner class Holder(private val bind: CardMenuBinding) : RecyclerView.ViewHolder(bind.root) {

		fun show(position: Int) {
			val text = callItem(position)
			val color = callColor(position)
			val img = callImageResources(position)
			bind.clColor.setBackgroundColor(color)
			bind.tvItemName.text = text
			bind.ivIcon.setImageResource(img)
			if (ColorType.isLight(color)) {
				bind.ivIcon.setColorFilter(ColorType.multiply(color, 1.1f))
				bind.tvItemName.setTextColor(itemView.context.getColor(R.color.black))
			} else {
				bind.ivIcon.setColorFilter(ColorType.multiply(color, 0.9f))
				bind.tvItemName.setTextColor(itemView.context.getColor(R.color.white))
			}
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
		return Holder(CardMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}

	override fun onBindViewHolder(holder: Holder, position: Int) {
		holder.show(position)
		holder.itemView.apply {
			val oneDP = context.resources.getDimension(R.dimen.one_dp)
			val lp = (layoutParams as GridLayoutManager.LayoutParams)
			val mar = (oneDP * 8).toInt()
			val marBorder = (oneDP * 16).toInt()

			//lp.isFullSpan = position % 3 == 0

			lp.setMargins(
				if (position % 2 == 0) marBorder else mar,
				mar,
				if (position % 2 == 1) marBorder else mar,
				mar
			)
			layoutParams = lp
		}
	}

	override fun getItemCount(): Int = callSize()


}

