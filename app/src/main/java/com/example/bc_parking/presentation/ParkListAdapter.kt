package com.example.bc_parking.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.bc_parking.R
import com.example.bc_parking.domain.ParkItem

class ParkListAdapter : ListAdapter<ParkItem, ParkItemViewHolder>(ParkItemDiffCallback()) {

    var onShopItemLongClickListener: ((ParkItem) -> Unit)? = null
    var onShopItemClickListener: ((ParkItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkItemViewHolder {
//        val layout = when (viewType) {
//            VIEW_TYPE_DISABLED -> R.layout.item_park
//            VIEW_TYPE_ENABLED -> R.layout.item_park
//            else -> throw RuntimeException("Unknown view type: $viewType")
//        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_park, parent, false)
        return ParkItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ParkItemViewHolder, position: Int) {
        val parkItem = getItem(position)
        viewHolder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(parkItem)
            true
        }
        viewHolder.view.setOnClickListener {
            onShopItemClickListener?.invoke(parkItem)
        }
        viewHolder.tvName.text = parkItem.name
        viewHolder.tvFirm.text = parkItem.firm
        viewHolder.tvDateFrom.text = parkItem.dateFrom
        viewHolder.tvDateTo.text = parkItem.dateTo
        viewHolder.tvCount.text = parkItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_ENABLED

    }

    companion object {

        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 30
    }
}