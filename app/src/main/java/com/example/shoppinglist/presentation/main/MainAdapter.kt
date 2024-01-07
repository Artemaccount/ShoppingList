package com.example.shoppinglist.presentation.main

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.domain.dto.ShopItem

@BindingAdapter("shop_list_adapter")
fun bindRecyclerView(recyclerView: RecyclerView, list: LiveData<List<ShopItem>>) {
    val adapter: ShopListAdapter = recyclerView.adapter as ShopListAdapter
    adapter.submitList(list.value)
}