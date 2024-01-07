package com.example.shoppinglist.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ShopItemBinding
import com.example.shoppinglist.domain.dto.ShopItem

interface OnShopItemClickInteractor {
    fun onClick(shopItem: ShopItem)
    fun onLongClick(shopItem: ShopItem)
}

class ShopListAdapter(
    private val onShopItemClickInteractor: OnShopItemClickInteractor
) : ListAdapter<ShopItem, ShopListViewHolder>(
    ShopListItemDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val binding = ShopItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShopListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        with(holder) {
            val item = getItem(position)
            binding.item = item
            if (item.enabled) {
                binding.cardItem.cardElevation = 10f
                binding.containerId.setBackgroundColor(holder.binding.cardItem.context.getColor(R.color.purple))
            } else {
                binding.cardItem.cardElevation = 0f
                binding.containerId.setBackgroundColor(holder.binding.cardItem.context.getColor(R.color.purple_light))
            }
            binding.containerId.setOnClickListener {
                onShopItemClickInteractor.onClick(item)
            }
            binding.containerId.setOnLongClickListener {
                onShopItemClickInteractor.onLongClick(item)
                true
            }
        }

    }
}

class ShopListViewHolder(val binding: ShopItemBinding) :
    RecyclerView.ViewHolder(binding.root)

class ShopListItemDiffCallback : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean =
        oldItem == newItem

}
