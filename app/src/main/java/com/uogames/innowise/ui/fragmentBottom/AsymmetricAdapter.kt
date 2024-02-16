package com.uogames.innowise.ui.fragmentBottom

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.uogames.innowise.R
import com.uogames.innowise.databinding.CardMenuBinding
import com.uogames.innowise.utils.ColorType

class AsymmetricAdapter(
	private val callSize: () -> Int,
	private val callItem: (position: Int) -> String,
	private val callColor: (position: Int) -> Int,
	private val callImageResources: (position: Int) -> Int
) : RecyclerView.Adapter<AsymmetricAdapter.Holder>() {


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
			val icon = findViewById<ImageView>(R.id.iv_icon)
			val p = position % 8
			val dp = resources.getDimension(R.dimen.one_dp)
			val lp = icon.layoutParams as ConstraintLayout.LayoutParams
			when (p) {
				1, 2, 4, 6 -> {
					lp.width = (dp * 50).toInt()
					lp.height = (dp * 50).toInt()
				}
//
				else -> {
					lp.width = (dp * 100).toInt()
					lp.height = (dp * 100).toInt()

				}
			}
		//	(layoutParams as SpannedGridLayoutManager.LayoutParams).apply {
				//setMargins(0, 0, 0, 0)
//				isFullSpan = when (p) {
//					3, 7 -> true
//					else -> false
//				}
		//	}
			(holder.itemView as MaterialCardView).apply {
				radius = 0f
			}
		}
	}

	override fun getItemCount(): Int = callSize()


}

