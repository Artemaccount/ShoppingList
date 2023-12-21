package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.dto.ShopItem

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun getShopItemList(): LiveData<List<ShopItem>>
    fun getShopItemById(id: Int): ShopItem
    fun updateShopItem(shopItem: ShopItem)
}